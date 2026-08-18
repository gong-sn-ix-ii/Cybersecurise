package com.develop.cybersecurise

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import android.Manifest
import android.view.Window
import androidx.core.content.ContextCompat
import com.develop.cybersecurise.models.ContactData
import com.develop.cybersecurise.services.AdapterCheckgon
import com.develop.cybersecurise.services.requestAPIWithChaladohn
import com.develop.cybersecurise.services.requestAPIWithCheckgon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InterceptCall : BroadcastReceiver() {

    private var isDialogShown = false
    private var dialogView: View? = null
    private var windowManager: WindowManager? = null
    private var oneway:Boolean = false

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { ctx ->
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return
            }

            try {
                val state: String? = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
                val phoneNumber: String? = intent?.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                Log.d("InterceptCall", "Phone state: $state")
                if (state != null) {
                    when (state) {
                        TelephonyManager.EXTRA_STATE_RINGING -> {
                            Log.d("InterceptCall", "Phone is ringing... $phoneNumber")
                            showAlertDialog(ctx, phoneNumber.orEmpty())
                        }
                        TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                            Log.d("InterceptCall", "Call received...")
                        }
                        TelephonyManager.EXTRA_STATE_IDLE -> {
                            Log.d("InterceptCall", "Phone idle...")
                            closeAlertDialog(ctx)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showAlertDialog(context: Context, phoneIncome: String) {
        val queryWithchaladohn = requestAPIWithChaladohn(context)
        val queryWithCheckgon = requestAPIWithCheckgon(context)

        val dataFromAPI: MutableMap<String, Int?> = mutableMapOf()

        val checkedModeChaladohn: String? = "phone"
        val checkedModeCheckgon: String? = "telephone"

        if (!Settings.canDrawOverlays(context)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            return
        }

        closeAlertDialog(context)

        if (!isDialogShown) {
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_safe_calling, null)

            windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

            val params = WindowManager.LayoutParams(
                900,
                800,
//                WindowManager.LayoutParams.WRAP_CONTENT,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else
                    WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT
            )

            params.gravity = Gravity.CENTER
            windowManager?.addView(view, params)

            val progressBar = view.findViewById<ProgressBar>(R.id.progress_information_progress_bar)
            val textDescript = view.findViewById<TextView>(R.id.text_state_scan_contact)

            dialogView = view
            isDialogShown = true
            //3 ขั้นตอน = 1.มีใน Contact [2.ไม่พบใน Contact -> 3.สแกน API]
            var runOneWay: String = "Null"

            progressBar.visibility = View.VISIBLE

            GlobalScope.launch {

                var textCheckgon:String = "\n"

                val firstStateScanCallingPhone = queryContactList(context, phoneIncome)
                withContext(Dispatchers.Main) {
                    if (firstStateScanCallingPhone) {
                        Log.d("InterceptCall", "FirstStateScanCallingPhone -> $phoneIncome มีใน contact")
                        textDescript.text = "$phoneIncome \nมีอยู่ใน contact"
                        runOneWay = "InContact"
                        progressBar.visibility = View.INVISIBLE
                    } else if (!firstStateScanCallingPhone) {
                        progressBar.visibility = View.VISIBLE
                        delay(50)
                        textDescript.text = "$phoneIncome\nไม่พบใน Contact \nระบบกำลังตรวจสอบ สเต็ปที่ 2\n"

                        queryWithchaladohn.queryDataFromAPI(mode = checkedModeChaladohn, numberID = phoneIncome, GETPOST = "GET") { data ->
                            dataFromAPI.putAll(data)

                            GlobalScope.launch(Dispatchers.Main) {
                                if (dataFromAPI["searches"] == null && dataFromAPI["report"] == null && dataFromAPI["sources"] == null) {
                                    textDescript.text = "$phoneIncome\nความเสี่ยงต่ำ\nยังไม่พบการร้องเรียน\nแหล่งอ้างอิง www.Chaladohn.com\n $textCheckgon"
                                } else {
                                    textDescript.text = "$phoneIncome \nจำนวนการค้นหา ${dataFromAPI["searches"]} ครั้ง\nจำนวนการร้องเรียน ${dataFromAPI["report"]} ครั้ง\nพบข้อมูลการร้องเรียน ${dataFromAPI["sources"]} แหล่ง\nแหล่งอ้างอิง www.Chaladohn.com"
                                }
                            }
                            progressBar.visibility = View.INVISIBLE
                        }

                        queryWithCheckgon.queryAPIDataFromCheckgon(group = "${checkedModeCheckgon}", value = "${phoneIncome}", "GET") { data ->
                            GlobalScope.launch(Dispatchers.Main) {
                                if(data?.data==null){
                                    textCheckgon += "ความเสี่ยงค่อนข้างต่ำ\nแหล่งอ้างอิง www.checkgon.com\n(โปรดพิจารณาก่อนตัดสินใจ)"
                                }else{
//                                    Log.d("API TESTING [DATA]", "${data.data[0]?.dataSource}")
//                                    binding.titleCheckgonID.text = searchValueCheckgon
                                    textCheckgon = if(data.severity=="high"){
                                        "ความเสี่ยง สูงมาก"
                                    }else if(data.severity=="medium"){
                                        "ความเสี่ยง ปานกลาง"
                                    }else{
                                        "ความเสี่ยง ต่ำ"
                                    }
                                    textCheckgon += "\nจำนวนการก่อเหตุ   ${data.total}  ครั้ง\nแหล่งอ้างอิง www.checkgon.com\n"
                                }
//                                textDescript.text = "${textDescript.text}\ncheckgon\n${textCheckgon}"
                            }
//                                progressBar.visibility = View.INVISIBLE
                        }


                    }
                }
            }

            view.setOnClickListener {
                removeDialog()
                Log.d("InterceptCall", "ปิด dialog เมื่อคลิก")
            }
        }
    }

    private fun closeAlertDialog(context: Context) {
        Log.d("InterceptCall", "Attempting to close dialog: isDialogShown=$isDialogShown, dialogView=$dialogView")
        if (isDialogShown && dialogView != null) {
            removeDialog()
            Log.d("InterceptCall", "Dialog closed")
        }
    }

    private fun removeDialog() {
        try {
            dialogView?.let {
                windowManager?.removeView(it)
                isDialogShown = false
                dialogView = null
                Log.d("InterceptCall", "Dialog removed")
            }
        } catch (e: Exception) {
            Log.e("InterceptCall", "Failed to close dialog", e)
        }
    }

    @SuppressLint("Range")
    fun queryContactList(context: Context, phoneIncome: String): Boolean {
        var inContact = false
        val contactList = mutableListOf<ContactData>()

        val cursor: Cursor? = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.let {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val photo = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                val internationalPhoneNumber = if (phoneNumber.startsWith("+")) {
                    phoneNumber
                } else {
                    "+66${phoneNumber.substring(1)}"
                }

                val photoBitmap: Bitmap? = if (photo != null) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(photo))
                } else {
                    BitmapFactory.decodeResource(context.resources, R.drawable.photo)
                }
                val contact = ContactData(name, phoneNumber, photoBitmap, internationalPhoneNumber)
                contactList.add(contact)
            }
            it.close()
        }

        contactList.forEach { data ->
            if (phoneIncome.isNotEmpty()) {
                if (data.phoneNumber == phoneIncome) {
                    Log.d("InterceptCall", "this phone number has contact $phoneIncome")
                    inContact = true
                } else {
                    Log.d("InterceptCall", "not found in contact $phoneIncome")
                }
            }
        }

        Log.d("InterceptCall", "status from contact $phoneIncome = $inContact")

        return inContact
    }
}
