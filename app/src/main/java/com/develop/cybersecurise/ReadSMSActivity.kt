package com.develop.cybersecurise

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.develop.cybersecurise.databinding.ActivityReadSmsactivityBinding
import com.develop.cybersecurise.models.CardData
import com.develop.cybersecurise.models.ContactData
import com.develop.cybersecurise.models.SMSMessageData
import com.develop.cybersecurise.services.AdapterSenderSMS
import com.develop.cybersecurise.services.SmsViewModel
import com.develop.cybersecurise.services.SystemService
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import java.io.File


class ReadSMSActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadSmsactivityBinding
    private val viewModel: SmsViewModel by viewModels()
    private var isDialogShown = false


    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReadSmsactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val allMessage = mutableMapOf<String, List<SMSMessageData>>()
        val getcontact = queryContractList()
        val queryMessage = readMessage(this, "sent", getcontact) + readMessage(this, "inbox", getcontact)
        val sortedMessages = queryMessage.sortedByDescending { it.date }

        allMessage += sortedMessages.groupBy { it.sender }

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            Log.d("Hello World", "Current user UID: ${currentUser.uid}")
        } else {
            Log.d("Hello World", "No user logged in.")
            auth.signInWithEmailAndPassword("admin@admin.ac.th", "1234567890")
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Log.d("Hello World", "signInWithEmail:success")
                        val user = auth.currentUser
                    } else {

                        Log.w("Hello World", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        viewModel.fetchDataFromApi(allMessage)

        viewModel.apiResponse.observe(this, Observer { response ->
            response?.forEach { (sender: String, detectResult: com.develop.cybersecurise.models.MessageSMSModels) ->
                Log.d("API Response", "Sender: $sender, Detect: ${detectResult.detect}")

                // Ensure the ListView is clickable after data is loaded
                binding.listviewSMS.isClickable = true

                // Initialize the adapter once
                val adapter = AdapterSenderSMS(this, allMessage)
                binding.listviewSMS.adapter = adapter

                // Set onItemClickListener for ListView
                binding.listviewSMS.setOnItemClickListener { parent, view, position, id ->
                    val keysList = allMessage.keys.toList()
                    val keys = keysList[position]
                    val selectedMessages: MutableMap<String, List<SMSMessageData>> = mutableMapOf(keys to allMessage[keys]!!)

                    // Pass the selected messages and keys to the next activity
                    SystemService().readBlockList(this)
                    val intent = Intent(this, MessageSMS::class.java).apply {
                        putExtra("sms_messages", HashMap(selectedMessages))
                        putExtra("keys_sms", keys)

                        // Pass the isBlocking status of the first message (if applicable)
                        selectedMessages[keys]?.firstOrNull()?.let { smsMessageData ->
                            putExtra("isBlocking", smsMessageData.isBlocking)
                        }
                    }

                    Log.d("Check Sender SMS and Message ALL", "$keys |")

                    startActivity(intent)
                }
            }
        })

        viewModel.errorMessage.observe(this, Observer { error ->
            error?.let {
                Log.e("API Error", it)
            }
        })

        binding.listviewSMS.isClickable = true
        val adapter = AdapterSenderSMS(this, allMessage)
        binding.listviewSMS.adapter = adapter

        binding.btnHelp.setOnClickListener {
            showAlertDialog()
        }

        binding.menuButton.setOnClickListener {
            val auths = FirebaseAuth.getInstance()
            val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
            val navView: NavigationView = findViewById(R.id.nav_view)

            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            if(auths.currentUser!=null){
                val nav_Header = navView.getHeaderView(0).findViewById<TextView>(R.id.user_email)
                nav_Header.text = auths.currentUser!!.email
            }else{
                val nav_Header = navView.getHeaderView(0).findViewById<TextView>(R.id.user_email)
                nav_Header.text = "โปรดล็อคอินเข้าสู่ระบบ"
            }
            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_scan_account -> {
                        startActivity(Intent(this, securiseAccountActivity::class.java))
                    }
                    R.id.nav_scan_phone -> {
                        startActivity(Intent(this, securisePhoneScamSpam::class.java))
                    }
                    R.id.nav_scan_SMS -> {
                        startActivity(Intent(this, ReadSMSActivity::class.java))
                    }
                    R.id.nav_settings -> {
                        startActivity(Intent(this, DisplayEnableSettings::class.java))
                    }
                    R.id.nav_appinstall -> {
                        startActivity(Intent(this, AppInstalledActivity::class.java))
                    }
                    R.id.nav_home -> {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    R.id.nav_tester -> {
                        startActivity(Intent(this, QuickCyberTest::class.java))
                    }
                    R.id.nav_culprit -> {
                        if (com.google.firebase.auth.FirebaseAuth.getInstance().currentUser != null) {
                            startActivity(Intent(this, ReportCenter::class.java))
                        } else {
                            Toast.makeText(this, "โปรด Login เข้าสู่ระบบก่อนใช้งานเมนู 'รายงานผู้ก่อนเหตุ'", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                true
            }
        }

    }

    @SuppressLint("Range")
    private fun queryContractList(): List<ContactData> {
        val contactList = mutableListOf<ContactData>()
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null,
        )

        cursor?.let {
            while (it.moveToNext()) {
                val name =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val photo =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                val internationalPhoneNumber = if (phoneNumber.startsWith("+")) {
                    phoneNumber
                } else {
                    "+66${phoneNumber.substring(1)}"
                }

                val photoBitmap: Bitmap? = if (photo != null) {
                    MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(photo))
                } else {
                    BitmapFactory.decodeResource(resources, R.drawable.photo)
                }
                val contact = ContactData(name, phoneNumber, photoBitmap, internationalPhoneNumber)
                contactList.add(contact)
            }
            it.close()
        }

        return contactList
    }

    private fun readMessage(context: Context, type: String, contactData: List<ContactData>): List<SMSMessageData> {
        val messages = mutableListOf<SMSMessageData>()
        val cursor = context.contentResolver.query(
            Uri.parse("content://sms/${type}"),
            null,
            null,
            null,
            null,
        )

        cursor?.use {
            val messageIndex = it.getColumnIndex("body")
            val senderIndex = it.getColumnIndex("address")
            val dateIndex = it.getColumnIndex("date")
            val readIndex = it.getColumnIndex("read")
            val typeIndex = it.getColumnIndex("type")
            val threadIndex = it.getColumnIndex("thread_id")

            while (it.moveToNext()) {
                val sender = it.getString(senderIndex)
                var isSpam = true

                //spam keyword
                val spamKeywords = listOf(
                    "EA", "KTB", "Steam", "facebook", "shopee", "lazada", "homepro",
                    "SPayLater", "true", "microsoft", "google", "True", "Trueyou",
                    "ais"
                ).map { it.toLowerCase() }

                for (contact in contactData) {
                    if (contact.phoneNumber.replace("\\s".toRegex(), "") == sender ||
                        contact.name == sender ||
                        spamKeywords.any { (sender.lowercase()).contains(it) } ||
                        spamKeywords.contains(sender.lowercase())
                    ) {
                        isSpam = false
                        break
                    }
                }
                if (isSpam) {
                    Log.d("check Sender testing", "sender $sender | $isSpam")
                }

                messages.add(
                    SMSMessageData(
                        message = it.getString(messageIndex),
                        sender = sender,
                        type = it.getInt(typeIndex),
                        date = it.getLong(dateIndex),
                        read = it.getString(readIndex).toBoolean(),
                        thread = it.getInt(threadIndex),
                        isSpam = isSpam,
                        isBlocking = SystemService().messageSMSBlockList(this, sender)
                    )
                )
            }
        }

        return messages
    }

    fun removeAllBlockedSender(senderToRemove: String) {
        val fileName = "CyberSecuriseBlockSMSList.txt"
        val file = File(filesDir, fileName)

        if (file.exists()) {
            var blockedSenders = file.readLines().toMutableList()

            // ทำการลบรายการที่ตรงกับ senderToRemove จนกว่าจะไม่มีชื่อในรายการ
            var isRemoved = true
            while (isRemoved) {
                isRemoved = blockedSenders.removeAll { it == senderToRemove }
            }

            // เขียนรายการที่เหลือกลับไปยังไฟล์
            file.writeText(blockedSenders.joinToString("\n"))

            if (blockedSenders.isEmpty()) {
                Log.d("REMOVE_BLOCK", "All instances of $senderToRemove have been removed from the block list.")
            } else {
                Log.d("REMOVE_BLOCK", "$senderToRemove has been removed from the block list.")
            }
        } else {
            Log.d("REMOVE_BLOCK", "Block list file does not exist.")
        }
    }


    private fun showAlertDialog() {
        if (!isDialogShown) {
            val view = layoutInflater.inflate(R.layout.dialog_info_sms, null)
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setView(view)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation_inUP_outDOWN
            alertDialog.show()

            val image = view.findViewById<ImageView>(R.id.image)
//            val detail = view.findViewById<TextView>(R.id.detail)

            isDialogShown = true
        } else {
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.dismiss()
            isDialogShown = false
        }
    }



}
