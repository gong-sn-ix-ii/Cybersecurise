package com.develop.cybersecurise

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.service.autofill.UserData
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.view.View
import android.widget.LinearLayout
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Locale
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.conding.cybersecurise.services.CardAdapter
import com.develop.cybersecurise.databinding.ActivityMainBinding
import com.develop.cybersecurise.models.CardData
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text
import java.util.Calendar


class MainActivity : AppCompatActivity() {


    private val TAG = "MainActivity Mumi Desuka"
    private val airplaneModeReceiver = AirplaneChangeMode()
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding

    lateinit var toggle: ActionBarDrawerToggle

    private val REQUEST_CODE_OVERLAY_PERMISSION = 101
    private val requestPermissionContact = 102
    private val REQUEST_CODE_SCHEDULE_EXACT_ALARM = 103
    private var isDialogShown = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val auth = FirebaseAuth.getInstance()

        Log.d("Hello World test current user ", auth.currentUser?.email.toString())

        registerReceiver(airplaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !getSystemService(AlarmManager::class.java).canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivityForResult(intent, REQUEST_CODE_SCHEDULE_EXACT_ALARM)
        } else {
            setAlarm()
        }

        binding.menuButton.setOnClickListener{
            val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
            val navView: NavigationView = findViewById(R.id.nav_view)

            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            if(auth.currentUser!=null){
                val nav_Header = navView.getHeaderView(0).findViewById<TextView>(R.id.user_email)
                val profile_none = navView.getHeaderView(0).findViewById<CircleImageView>(R.id.profile_user_none)
                val profile_have = navView.getHeaderView(0).findViewById<LinearLayout>(R.id.profile_user_have)
                val text_in_profile = navView.getHeaderView(0).findViewById<TextView>(R.id.text_in_profile)
                nav_Header.text = auth.currentUser!!.email
                profile_have.visibility = View.VISIBLE
                profile_none.visibility = View.GONE
                text_in_profile.text = auth.currentUser!!.email?.substring(0, 1)?.uppercase(Locale.ROOT)
            }else{
                val nav_Header = navView.getHeaderView(0).findViewById<TextView>(R.id.user_email)
                val profile_none = navView.getHeaderView(0).findViewById<CircleImageView>(R.id.profile_user_none)
                val profile_have = navView.getHeaderView(0).findViewById<LinearLayout>(R.id.profile_user_have)
                profile_have.visibility = View.GONE
                profile_none.visibility = View.VISIBLE
                nav_Header.text = "โปรดล็อคอินเข้าสู่ระบบ"
            }
            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.nav_scan_account -> {
                        val intent = Intent(this, securiseAccountActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.nav_scan_phone -> {
                        val intent = Intent(this, securisePhoneScamSpam::class.java)
                        startActivity(intent)
                    }
                    R.id.nav_scan_SMS -> {
                        val intent = Intent(this, ReadSMSActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.nav_settings -> {
                        val intent = Intent(this, DisplayEnableSettings::class.java)
                        startActivity(intent)
                    }
                    R.id.nav_appinstall -> {
                        val intent = Intent(this, AppInstalledActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.nav_home -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.nav_tester -> {
                        val intent = Intent(this, QuickCyberTest::class.java)
                        startActivity(intent)
                    }
                    R.id.nav_culprit -> {
                        if(auth.currentUser!=null){
                            val intent = Intent(this, ReportCenter::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "โปรด Login เข้าสู่ระบบก่อนใช้งานเมนู 'รายงานผู้ก่อนเหตุ'", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                true
            }
        }

        binding.btnAccount.setOnClickListener {
            if(auth.currentUser!=null){
                val intent = Intent(this, Signout_Account::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, LoginAndRegis::class.java)
                startActivity(intent)
            }
        }

        binding.account.setOnClickListener {
            val intent = Intent(this, securiseAccountActivity::class.java)
            startActivity(intent)
        }

        binding.phone.setOnClickListener {
            val intent = Intent(this, securisePhoneScamSpam::class.java)
            startActivity(intent)
        }

        binding.SMS.setOnClickListener {
            val intent = Intent(this, ReadSMSActivity::class.java)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adviceDataList = mutableListOf<CardData>()

        val iconSMS_advice: Drawable = ContextCompat.getDrawable(this, R.drawable.fake_sms_logo)!!
        val iconWebFake_advice: Drawable = ContextCompat.getDrawable(this, R.drawable.facebook_fake_logo)!!
        val iconEmailSpam_advice: Drawable = ContextCompat.getDrawable(this, R.drawable.email_spam_logo)!!
        val iconLinkFake_advice: Drawable = ContextCompat.getDrawable(this, R.drawable.fake_link_logo)!!
        val iconLineFake_advice: Drawable = ContextCompat.getDrawable(this, R.drawable.line_fake_logo)!!

        adviceDataList.add(CardData(iconEmailSpam_advice,"Email", "เมื่อได้รับ Email ปลอม จะตั้งปฏิบัติดังนี", "เมื่อได้รับ E-Mail ปลอม\n" +
                "ควรปฏิบัติตามขั้นตอน\"\n" +
                "-อย่าคลิกลิงก์ที่แนบมากับอีเมล์ปลอม เพราะอาจนำไป\n" +
                "สู่เว็บไซต์ที่อันตรายหรือมีภัยคุกคาม\n" +
                "-อย่าดาวน์โหลดหรือเปิดรูปภาพ, ไฟล์, หรือเอกสารที่\n" +
                "แนบมากับอีเมล์ปลอม เพราะอาจมีไวรัสหรือมัลแวร์ที่\n" +
                "ส่งผลกระทบต่อคอมพิวเตอร์หรือข้อมูลส่วนตัว\n" +
                "-อย่าตอบกลับอีเมล์ปลอม เพราะส่วนใหญ่เป็นการยืนยัน\n" +
                "ว่าอีเมล์ของคุณมีอยู่และเปิดอ่านโดยผู้ไม่ประสงค์ดี\n" +
                "-ลบอีเมล์ดังกล่าวทิ้งทันทีเพื่อป้องกันการเปิดอ่านอีเมล์\n" +
                "ที่ไม่พึงประสงค์อีกครั้ง\n" +
                "อีเมล์ที่ถูกส่งเพื่อหลอกเอาข้อมูลส่วนตัว เช่น รหัสผ่าน, \n" +
                "หมายเลขบัตรประจำตัวประชาชน, เลขบัญชี และข้อมูลสำคัญอื่น ๆ \n" +
                "เรียกว่า Phishing Email หรืออีเมล์ปลอม ชักจูงคนเข้าสู่การล่อลวง \n" +
                "ผู้ส่งอีเมลปลอมจะพยายามเจาะจงให้เหยื่อเผยแพร่ข้อมูลส่วนตัวที่อาจก่อให้\n" +
                "เกิดความเสียหายหรือการถูกโจมตีทางอินเทอร์เน็ตหรือมีวิธีที่ค่อนข้างคล้าย\n" +
                "กับการช่อโกงในรูปแบบ SMS คือล่อให้คุณคลิงก์ภายในอีเมลแทนนั่นเอง\n" +
                "อีเมล์ปลอมเหล่านี้มักใช้ชื่อบัญชีอีเมล์ที่ไม่ตรงกับชื่อบริษัท, องค์กร, หรือ\n" +
                "หน่วยงานที่แอบอ้างว่าเป็นตัวแทน นอกจากนี้ ข้อความในอีเมล์ปลอม\n" +
                "มักเขียนเป็นภาษาอังกฤษหรือภาษาไทยที่มีการสะกดผิดหรือดูผิดปกติ \n" +
                "ซึ่งต่างจากอีเมล์ทางการที่มาจากสถาบันต่างๆ"))
        adviceDataList.add(CardData(iconSMS_advice,"SMS", "เมื่อได้รับ SMS ปลอม จะตั้งปฏิบัติดังนี", "สิ่งที่ควรปฏิบัติเมื่อได้รับ\n" +
                "SMS ปลอม\"\n" +
                "-ติดต่อสอบถามจากบริษัท องค์กร หรือหน่วยงาน \n" +
                "-ต้นทางเพื่อตรวจสอบข้อเท็จจริงเกี่ยวกับ SMS ที่ได้รับ\n" +
                "-กด block and report spam เพื่อป้องกันข้อความอื่น\n" +
                "จากเบอร์โทรศัพนั้น\n" +
                "-ไม่คลิกลิงก์ที่แนบมากับ SMS ปลอม\n" +
                "“มิจฉาชีพออนไลน์” มักใช้ชื่อบริษัทหรือองค์กรที่น่าเชื่อถือและเป็นที่รู้จักส่ง \n" +
                "SMS มาให้กับคุณ เช่น หน่วยงานของรัฐและสถาบันการเงิน ซึ่งมิจฉาชีพ\n" +
                "เหล่านี้มักเสนอข้อเสนอพิเศษให้ผู้ถือหมายเลขรับสิทธิ์เปรียบเสมือนเหยื่อ\n" +
                "ที่ใช้ล่อให้คุณมาติดกับนั่นเอง ข้อเสนอเหล่านี้มีรูปแบบมากมายไม่ว่าจะมา\n" +
                "ในแนวทางของ ของขวัญหรือสิ่งที่มีค่าสมนาคุณ โดยจะส่งเป็น SMS เพื่อ\n" +
                "พยายามล่อให้คุณคลิกลิงก์เพื่อรับสิทธิ์นั้นๆ "))
        adviceDataList.add(CardData(iconLinkFake_advice,"Short Link", "ลิ้งค์อันตรายที่จะติดต้ังแอปลงเครื่องของคุณ", "สิ่งที่ควรปฏิบัติเมื่อได้รับ\n" +
                "ลิงก์เว็บไซต์ปลอม\"\n" +
                "-ห้ามคลิกเข้าสู่เว็บไซต์โดยเด็ดขาด และสามารถนำ URL \n" +
                "ของเว็บไซต์ต้องสงสัยไปตรวจสอบได้ที่เว็บไซต์ \n" +
                "https://www.whois.com/whois ซึ่งจะระบุประเทศ\n" +
                "ที่จดทะเบียนเว็บไซต์นั้น ๆ โดยเว็บไซต์ที่จดทะเบียนต่าง\n" +
                "ประเทศถือว่ามีความเสี่ยงสูง\n" +
                "-หากเผลอคลิกเข้าสู่เว็บไซต์ไปแล้ว ห้ามคลิกปุ่มหรือ \n" +
                "URL อื่น ๆ ที่ปรากฏอยู่ในเว็บไซต์โดยเด็ดขาด ให้คลิก\n" +
                "ปิดเว็บไซต์ทันที\n" +
                "-ติดต่อกับบริษัท องค์กร หรือหน่วยงานเพื่อตรวจสอบ\n" +
                "ข้อเท็จจริงเกี่ยวกับเว็บไซต์นั้น ๆ\n" +
                "ชื่อ URL อาจะจะถูกเปลี่ยนเล็กน้อยเพื่อทำให้แตกต่างจากเว็บไซต์ทางการ\n" +
                "ของบริษัท องค์กร หรือหน่วยงานนั้นๆ หากไม่สังเกตอย่างละเอียด อาจเชื่อ\n" +
                "ว่าเป็นเว็บไซต์จริงได้ เช่น www.moqh.in.th เป็นเว็บไซต์ปลอมที่แอบอ้าง\n" +
                "ชื่อกระทรวงสาธารณสุข ในขณะที่เว็บไซต์จริงใช้ URL www.moph.go.th\n" +
                "อย่างไรก็ตาม URL ของเว็บไซต์ปลอมอาจไม่มีความคล้ายคลึงกับเว็บไซ\n" +
                "ต์ทางการของหน่วยงานที่ถูกแอบอ้าง โดยสิ้นเชิง เช่น การแอบอ้างชื่อ\n" +
                "กระทรวงการคลังโดยใช้ URL https://acwc9.com ซึ่งแตกต่างจาก\n" +
                "เว็บไซต์จริงของกระทรวงการคลัง     มิจฉาชีพออนไลน์อาจส่ง URL \n" +
                "ของเว็บไซต์ปลอมผ่านทาง SMS, LINE, หน้าเพจโซเชียลมีเดีย รวมถึงหน้า\n" +
                "ค้นหาของ Google เพื่อสร้างความสับสนและกดดาวน์โหลดโปรแกรมที่\n" +
                "ไม่เหมาะสมหรือชำระค่าบริการที่สูงกว่าความเป็นจริง"))
        adviceDataList.add(CardData(iconWebFake_advice,"Facebook ปลอม", "สิ่งที่ควรปฏิบัติและวิธีเช็ค Facebook ปลอม", "สิ่งที่ควรปฏิบัติเมื่อเจอ\n" +
                "Facebook ปลอม\"\n" +
                "-ไม่กดไลค์หรือติดตาม เพื่อป้องกันความสับสนจาก\n" +
                "ข้อมูลที่ Facebook ปลอมนำเสนอ\n" +
                "-รายงานความผิดปกติให้กับทาง Facebook\n" +
                "Facebook ปลอมนั้นที่จริงแล้วสามารถตรวจสอบได้ไม่ยากเลย\n" +
                "สามารถสังเกตได้โดยการเช็ค Verified badge ที่เป็นรูปเครื่องหมายถูก\n" +
                "ในวงกลมสีฟ้าที่อยู่หลังชื่อเพจ ถ้าเป็น Facebook จริงจะมี Verified badge \n" +
                "ในขณะที่ Facebook ปลอมอาจใช้ชื่อ โลโก้ รูปภาพ หรือใช้ภาษาที่คล้ายกัน\n" +
                "กับ Facebook จริง แต่จะไม่มี Verified badge เหล่านี้\n" +
                "\n" +
                "นอกจากความเสี่ยงจาก Facebook ปลอมที่ลอกเลียนแบบเพจของบริษัท\n" +
                "องค์กร หรือหน่วยงานต่าง ๆ ยังมีความเป็นไปได้ที่จะถูกขโมยอัตลักษณ์\n" +
                "โดยมิจฉาชีพออนไลน์ ซึ่งอาจบันทึกรูปภาพของเราแล้วนำไปเปิด Facebook \n" +
                "ปลอมเพื่อหลอกลวงเพื่อเอาเงินหรือทรัพย์สินจากผู้อื่นได้ \n" +
                "ดังนั้น ควรระมัดระวังและระวังตัวในการตอบรับคำขอเป็นเพื่อนจากบุคคล\n" +
                "ที่ไม่รู้จัก และจำกัดการโพสต์รูปภาพให้เฉพาะบัญชีของผู้ที่เป็นเพื่อนเท่านั้น\n" +
                "ที่สามารถเข้าถึงได้"))

        adviceDataList.add(CardData(iconLineFake_advice,"Line ปลอม", "สิ่งที่ควรปฏิบัติและวิธีเช็ค Line ปลอม", "เมื่อพบ LINE ปลอมควรปฏิบัติตามขั้นตอน\n" +
                "-อย่าคลิกเพิ่มเพื่อน (add friend) เด็ดขาด เพื่อป้องกันการเข้าสู่ระบบของ LINE ปลอมเข้าสู่บัญชีของคุณ\n" +
                "-บล็อก LINE ปลอมทันที เพื่อป้องกันการรับข้อความหรือการติดต่อจากผู้ใช้งาน LINE ปลอม\n" +
                "-รายงาน (Report) LINE ปลอมให้กับ LINE เพื่อให้พวกเขาทราบถึงการละเมิดและดำเนินการตามนโยบายของพื้นที่ LINELINE ปลอมนั้นค่อนข้างระบาดหนักและมีรูปแบบที่หลากหลาย ไม่ว่าจะเป็นเรื่องของการให้เงินกู้ บางครั้งจะแสดงตัวชัดเจนว่าเป็นผู้ให้เงินกู้ แต่บางครั้งก็จะแฝงตัวมาในรูปของร้านค้าต่างๆ รวมถึงแอบอ้างว่าเป็นบริษัท องค์กร หรือหน่วยงานที่รู้จักด้วย   อย่างไรก็ตาม การสังเกต LINE ปลอมนั้นเป็นเรื่องที่ไม่ยากเลย เนื่องจากบัญชี LINE Official account จะแสดงสถานะของบัญชีเป็นรูปโลโก้สีเขียวสำหรับธุรกิจหรือองค์กรขนาดใหญ่ หรือสถานะเป็นรูปโลโก้สีน้ำเงินสำหรับบัญชีที่ได้รับการรับรองแล้ว"))

        binding.recyclerView.isClickable = true
        val adapter = CardAdapter(this, adviceDataList)
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener { position ->
            showAlertDialog(adviceDataList[position])
        }

        checkAndRequestPermissions()
//        startForegroundService(Intent(this, PhoneStateService::class.java))


    }

    private fun showAlertDialog(data: CardData) {
        if (!isDialogShown) {
            val view = layoutInflater.inflate(R.layout.dialog_layout, null)
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setView(view)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation_inUP_outDOWN
            alertDialog.show()

            val image = view.findViewById<ImageView>(R.id.image)
            val detail = view.findViewById<TextView>(R.id.detail)
            image.setImageDrawable(data.icon)
            detail.text = data.alert_detail

            isDialogShown = true
        } else {
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.dismiss()
            isDialogShown = false
        }
    }



    private fun checkAndRequestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_SMS,
            Manifest.permission.WRITE_CONTACTS
        )

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), requestPermissionContact)
        }

        if (!Settings.canDrawOverlays(this)) {
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            myIntent.data = Uri.parse("package:$packageName")
            startActivityForResult(myIntent, REQUEST_CODE_OVERLAY_PERMISSION)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestPermissionContact) {
            val allPermissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (allPermissionsGranted) {
                recreate()
            } else {
                Toast.makeText(this, "All permissions are required for this app", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_OVERLAY_PERMISSION -> {
                if (Settings.canDrawOverlays(this)) {
                    // Permission granted, proceed with your logic
                } else {
                    Toast.makeText(this, "Overlay permission is required for this app", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_CODE_SCHEDULE_EXACT_ALARM -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && getSystemService(AlarmManager::class.java).canScheduleExactAlarms()) {
                    setAlarm()
                } else {
                    Log.e("MainActivity Mumi Desuka", "Permission not granted")
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setAlarm() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, 3)

        val intent = Intent(this, AirplaneChangeMode::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        try {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager, AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } catch (e: Exception) {
            Log.e("MainActivity Mumi Desuka", "Failed to set alarm", e)
        }
    }

}


