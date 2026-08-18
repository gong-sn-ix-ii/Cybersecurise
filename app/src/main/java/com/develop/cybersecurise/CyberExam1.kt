package com.develop.cybersecurise

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.quizapp.QuestionAdapter
import com.develop.cybersecurise.databinding.ActivityCyberExam1Binding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class CyberExam1 : AppCompatActivity() {

    private lateinit var binding: ActivityCyberExam1Binding

    private val questions = listOf(
        listOf("1. เมื่อคุณได้รับข้อความ SMS ที่ดูเหมือนมาจากธนาคาร แต่ขอข้อมูลส่วนตัวของคุณ คุณควรทำอย่างไร?",
            "ให้ข้อมูลตามที่ขอ",
            "ตรวจสอบความถูกต้องของผู้ส่งและไม่ให้ข้อมูลส่วนตัว",
            "ลบทันทีโดยไม่อ่าน",
            "ส่งต่อข้อความให้เพื่อน",
            "คำตอบที่ถูกคือ 2"),

        listOf("2. หากคุณได้รับอีเมลจากคนที่ไม่รู้จักและมีไฟล์แนบ คุณควรทำอย่างไร?",
            "ดาวน์โหลดและเปิดไฟล์แนบทันที",
            "ตอบกลับอีเมลเพื่อขอข้อมูลเพิ่มเติม",
            "ไม่เปิดไฟล์แนบและลบอีเมล",
            "ส่งต่ออีเมลให้คนอื่น",
            "คำตอบที่ถูกคือ 3"),

        listOf("3. การตั้งรหัสผ่านที่ปลอดภัยควรมีลักษณะอย่างไร?",
            "ประกอบด้วยตัวอักษร ตัวเลข และสัญลักษณ์พิเศษ",
            "เป็นคำง่ายๆ ที่จำง่าย",
            "ใช้วันเกิดของคุณ",
            "ใช้รหัสผ่านเดียวกันกับทุกบัญชี",
            "คำตอบที่ถูกคือ 1"),

        listOf("4. เมื่อใช้งาน Wi-Fi สาธารณะ สิ่งที่ควรระมัดระวังที่สุดคืออะไร?",
            "การดาวน์โหลดแอปพลิเคชันใหม่",
            "การทำธุรกรรมทางการเงิน",
            "การดูวิดีโอออนไลน์",
            "การอ่านข่าว",
            "คำตอบที่ถูกคือ 2"),

        listOf("5. คุณควรทำอย่างไรเมื่อพบว่าบัญชีโซเชียลมีเดียของคุณถูกแฮ็ก?",
            "เปลี่ยนรหัสผ่านทันที",
            "โพสต์บนโซเชียลมีเดียว่าบัญชีถูกแฮ็ก",
            "ไม่ทำอะไรและรอดูว่าเกิดอะไรขึ้น",
            "สร้างบัญชีใหม่ทันที",
            "คำตอบที่ถูกคือ 1"),

        listOf("6. ข้อใดคือวิธีการที่ดีที่สุดในการป้องกันข้อมูลส่วนตัวจากการโดนขโมย?",
            "ใช้ซอฟต์แวร์ป้องกันไวรัสที่อัพเดตอยู่เสมอ",
            "ไม่ใช้รหัสผ่าน",
            "แชร์ข้อมูลส่วนตัวบนโซเชียลมีเดีย",
            "เปิดเผยข้อมูลสำคัญกับทุกคน",
            "คำตอบที่ถูกคือ 1"),

        listOf("7. การทำธุรกรรมออนไลน์ ควรทำผ่านช่องทางใด?",
            "เครือข่าย Wi-Fi สาธารณะ",
            "เครือข่ายอินเทอร์เน็ตที่มีการเข้ารหัสและปลอดภัย",
            "คอมพิวเตอร์ของเพื่อน",
            "ร้านอินเทอร์เน็ต",
            "คำตอบที่ถูกคือ 2"),

        listOf("8. การอัพเดตซอฟต์แวร์บ่อยๆ มีความสำคัญอย่างไร?",
            "เพื่อให้คอมพิวเตอร์ทำงานช้าลง",
            "เพื่อเพิ่มฟังก์ชั่นใหม่ๆ ที่น่าสนใจ",
            "เพื่อป้องกันและแก้ไขช่องโหว่ด้านความปลอดภัย",
            "เพื่อให้ซอฟต์แวร์ใช้งานง่ายขึ้น",
            "คำตอบที่ถูกคือ 3"),

        listOf("9. การใช้บริการ Cloud Storage ควรระมัดระวังอย่างไร?",
            "เก็บข้อมูลที่สำคัญและเป็นความลับ",
            "แชร์รหัสผ่านกับเพื่อน",
            "ไม่ต้องตั้งรหัสผ่านในการเข้าถึง",
            "ตั้งค่าความปลอดภัยและการเข้ารหัสข้อมูล",
            "คำตอบที่ถูกคือ 4"),

        listOf("10. การใช้แอปพลิเคชันธนาคารบนมือถือ ควรทำอย่างไรเพื่อให้ปลอดภัย?",
            "ดาวน์โหลดแอปพลิเคชันจากแหล่งที่ไม่เป็นทางการ",
            "ตั้งค่าการยืนยันตัวตนแบบสองชั้น (2FA)",
            "ใช้รหัสผ่านที่ง่ายต่อการคาดเดา",
            "ไม่ต้องออกจากระบบหลังจากใช้งาน",
            "คำตอบที่ถูกคือ 2")
    )

    private lateinit var questionListView: ListView
    private lateinit var submitButton: Button
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCyberExam1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        questionListView = binding.questionListView
        submitButton = binding.submitButton

        questionAdapter = QuestionAdapter(this, questions)
        questionListView.adapter = questionAdapter

        submitButton.setOnClickListener {
            if (!questionAdapter.checkAnswers()) {
                Toast.makeText(this, "กรุณาตอบคำถามให้ครบก่อน", Toast.LENGTH_SHORT).show()
            } else {
                questionAdapter.notifyDataSetChanged()
                for (i in questions.indices) {
                    questionAdapter.setAnswered(i)
                }
                Toast.makeText(this, "ส่งคำตอบ", Toast.LENGTH_SHORT).show()
            }
        }



        val menuButton = findViewById<ImageButton>(R.id.menuButton)
        menuButton.setOnClickListener {
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
}
