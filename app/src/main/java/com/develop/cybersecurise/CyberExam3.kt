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

class CyberExam3 : AppCompatActivity() {

    private lateinit var binding: ActivityCyberExam1Binding

    val questions = listOf(
        listOf(
            "1. การเชื่อมต่อ VPN มีประโยชน์อย่างไรต่อความปลอดภัยของข้อมูล?",
            "ลดความเสี่ยงจากการถูกแฮ็กเครือข่าย",
            "เข้าถึงเว็บไซต์ที่ถูกระบบตรวจจับและบล็อกไว้",
            "ทำให้สามารถเรียกดูข้อมูลที่ไม่ได้รับอนุญาตได้",
            "ช่วยให้การใช้งานเว็บไซต์เร็วขึ้น",
            "คำตอบที่ถูกคือ 1"
        ),
        listOf(
            "2. ฟังก์ชันการตรวจสอบความปลอดภัยในเว็บเบราว์เซอร์ทำหน้าที่อะไร?",
            "ป้องกันการแสดงโฆษณาที่ไม่พึงประสงค์",
            "ป้องกันไม่ให้เว็บไซต์ทำงานได้ปกติ",
            "ควบคุมการเข้าถึงข้อมูลส่วนตัว",
            "แสดงเครื่องหมายการเข้ารหัสของเว็บไซต์",
            "คำตอบที่ถูกคือ 4"
        ),
        listOf(
            "3. การสร้างรหัสผ่านที่ปลอดภัยควรทำอย่างไร?",
            "ใช้แค่ตัวอักษรพิมพ์เล็ก",
            "ใช้วันเกิดเป็นรหัสผ่าน",
            "ประกอบด้วยตัวอักษร ตัวเลข และสัญลักษณ์พิเศษ",
            "ใช้รหัสผ่านเดียวกันทุกที่",
            "คำตอบที่ถูกคือ 3"
        ),
        listOf(
            "4. การตรวจสอบ URL ก่อนคลิกเป็นการป้องกันตัวเองจากอะไร?",
            "ไวรัสคอมพิวเตอร์",
            "การหลอกลวงหรือการโจมตีทางไซเบอร์",
            "การโจมตีแฮ็กเกอร์",
            "การปิดการใช้งานเครือข่าย",
            "คำตอบที่ถูกคือ 2"
        ),
        listOf(
            "5. การเข้ารหัสข้อมูลมีประโยชน์อย่างไรต่อความปลอดภัยของข้อมูล?",
            "ป้องกันการถูกเก็บข้อมูลส่วนตัว",
            "ลดความเสี่ยงจากการถูกแฮ็กเครือข่าย",
            "ทำให้ข้อมูลเป็นมาตรฐานสากล",
            "ทำให้ข้อมูลสามารถถูกเข้าถึงได้ง่ายขึ้น",
            "คำตอบที่ถูกคือ 2"
        ),
        listOf(
            "6. การปฏิบัติตามหลักการ 'เปิดตัว' (Open Principle) ในความปลอดภัยหมายถึงอะไร?",
            "เปิดเผยข้อมูลส่วนตัวให้กับทุกคน",
            "เปิดใช้งานระบบให้กับบุคคลภายนอก",
            "เปิดให้ทราบเทคโนโลยีที่ใช้ในระบบ",
            "เปิดให้ทราบรายละเอียดเกี่ยวกับข้อบกพร่องของระบบ",
            "คำตอบที่ถูกคือ 4"
        ),
        listOf(
            "7. การเก็บข้อมูลที่ปลอดภัยควรปฏิบัติอย่างไร?",
            "ไม่ต้องมีการเข้ารหัสข้อมูล",
            "ใช้รหัสผ่านที่ง่ายต่อการคาดเดา",
            "ตรวจสอบความถูกต้องของซอฟต์แวร์ป้องกันไวรัส",
            "ตั้งค่าความปลอดภัยและการเข้ารหัสข้อมูล",
            "คำตอบที่ถูกคือ 4"
        ),
        listOf(
            "8. การอัพเดตซอฟต์แวร์ที่สำคัญเพื่อความปลอดภัยทำประโยชน์อย่างไร?",
            "เพื่อป้องกันและแก้ไขช่องโหว่ด้านความปลอดภัย",
            "เพื่อให้คอมพิวเตอร์ทำงานช้าลง",
            "เพื่อให้ซอฟต์แวร์ใช้งานง่ายขึ้น",
            "เพื่อเพิ่มฟังก์ชั่นใหม่ๆ ที่น่าสนใจ",
            "คำตอบที่ถูกคือ 1"
        ),
        listOf(
            "9. การใช้บริการ Cloud Storage ควรปฏิบัติอย่างไรเพื่อรักษาความปลอดภัยของข้อมูล?",
            "เปิดเผยรหัสผ่านกับบุคคลที่ไม่เป็นทางการ",
            "ไม่ต้องตั้งรหัสผ่านในการเข้าถึง",
            "ตั้งค่าความปลอดภัยและการเข้ารหัสข้อมูล",
            "ไม่จำเป็นต้องใช้การเข้ารหัสข้อมูล",
            "คำตอบที่ถูกคือ 3"
        ),
        listOf(
            "10. การใช้งานแอปพลิเคชันธนาคารบนมือถือ ควรทำอย่างไรเพื่อรักษาความปลอดภัยของข้อมูล?",
            "ไม่ต้องออกจากแอปพลิเคชันหลัก",
            "ไม่เข้าถึงหน้าจอที่มีข้อมูลส่วนตัว",
            "ตั้งค่ารหัสผ่านที่ง่ายจะคาดเดาได้",
            "อัพเดตแอปพลิเคชันให้เป็นเวอร์ชันล่าสุด",
            "คำตอบที่ถูกคือ 4"
        ),
        listOf(
            "11. การใช้งานอินเทอร์เน็ตสาธารณะ (Public Wi-Fi) ควรปฏิบัติอย่างไรเพื่อความปลอดภัยของข้อมูล?",
            "ไม่ต้องใช้ VPN",
            "ป้องกันไม่ให้ข้อมูลส่วนตัวหลุดออกไป",
            "ใช้การเข้ารหัสข้อมูลที่มีความปลอดภัย",
            "เชื่อมต่อแบบไม่มีการควบคุม",
            "คำตอบที่ถูกคือ 3"
        ),
        listOf(
            "12. การเข้ารหัสข้อมูล (Encryption) มีประโยชน์อย่างไรต่อความปลอดภัยของข้อมูล?",
            "ทำให้ข้อมูลสามารถถูกเข้าถึงได้ง่ายขึ้น",
            "ป้องกันการถูกแฮ็กเครือข่าย",
            "ลดความเสี่ยงจากการถูกตรวจจับโดยบุคคลที่ไม่ใช่เจ้าของข้อมูล",
            "ทำให้ข้อมูลเป็นมาตรฐานสากล",
            "คำตอบที่ถูกคือ 2"
        ),
        listOf(
            "13. การใช้งานอีเมลล์ ควรระวังอะไรเพื่อป้องกันการโจมตีทางไซเบอร์?",
            "อ่านอีเมลล์จากผู้ส่งที่ไม่รู้จัก",
            "คลิกลิงค์ที่มีลักษณะเฉพาะที่มาจากแหล่งที่มีความน่าเชื่อถือ",
            "ทำตามคำแนะนำที่ให้โดยอีเมลล์ที่ไม่ได้ขอ",
            "ปิดการใช้งานระบบอีเมลล์",
            "คำตอบที่ถูกคือ 2"
        ),
        listOf(
            "14. การเตรียมการสำหรับการโจมตีด้วยการหลอกลวง (Phishing Attack) ควรทำอย่างไร?",
            "ไม่คลิกลิงค์จากอีเมลล์ที่ไม่คาดหวัง",
            "ให้ข้อมูลส่วนตัวตามที่ขอ",
            "ตรวจสอบความถูกต้องของผู้ส่ง",
            "ส่งต่อข้อความให้เพื่อน",
            "คำตอบที่ถูกคือ 1"
        ),
        listOf(
            "15. การบันทึกข้อมูล (Logging) เป็นการปฏิบัติอย่างไรที่สำคัญเพื่อความปลอดภัยของระบบ?",
            "บันทึกข้อมูลทุกกิจกรรมที่เกิดขึ้นในระบบ",
            "ไม่จำเป็นต้องบันทึกข้อมูลการเข้าถึงระบบ",
            "บันทึกเฉพาะข้อมูลที่เป็นสาธารณะ",
            "ลบข้อมูลบันทึกออกทุกๆ 7 วัน",
            "คำตอบที่ถูกคือ 1"
        ),
        listOf(
            "16. การป้องกันการโจมตีด้วยการเรียกหาผ่านโทรศัพท์ (Vishing Attack) ควรทำอย่างไร?",
            "ไม่รับโทรศัพท์ที่ไม่รู้จักหมายเลข",
            "ตรวจสอบความถูกต้องของเบอร์โทรศัพท์",
            "ไม่รับสายที่มาจากบริษัทที่คุ้นเคย",
            "ส่งข้อมูลส่วนตัวผ่านโทรศัพท์",
            "คำตอบที่ถูกคือ 1"
        ),
        listOf(
            "17. การใช้งานเครือข่ายสังคมออนไลน์ (Social Media) ควรระวังอะไร?",
            "ไม่ให้ข้อมูลส่วนตัวแก่ผู้ใช้งานที่ไม่รู้จัก",
            "โพสต์ข้อมูลส่วนตัวบนเครือข่ายสังคม",
            "แชร์ข้อมูลส่วนตัวให้กับแอปพลิเคชันต่างๆ",
            "ใช้พาสเวิร์ดที่ง่ายต่อการคาดเดา",
            "คำตอบที่ถูกคือ 1"
        ),
        listOf(
            "18. การใช้งานบริการ Cloud Computing ควรระวังอะไรเพื่อความปลอดภัยของข้อมูล?",
            "ไม่ต้องใช้การเข้ารหัสข้อมูล",
            "ใช้รหัสผ่านที่ง่ายต่อการคาดเดา",
            "ตั้งค่าความปลอดภัยและการเข้ารหัสข้อมูล",
            "เปิดเผยข้อมูลส่วนตัวให้กับบุคคลที่ไม่เป็นทางการ",
            "คำตอบที่ถูกคือ 3"
        ),
        listOf(
            "19. การสแปมอีเมลล์ (Spam Email) เป็นปัญหาในด้านความปลอดภัยอย่างไร?",
            "เป็นการโจมตีทางไซเบอร์ที่ใช้กับเว็บไซต์",
            "เป็นปัญหาของระบบการจัดการข้อมูล",
            "เป็นการกระทำที่เพิ่มความเสี่ยงจากไวรัสคอมพิวเตอร์",
            "เป็นเทคนิคที่ใช้ในการส่งข้อมูลส่วนตัว",
            "คำตอบที่ถูกคือ 3"
        ),
        listOf(
            "20. การใช้งานอุปกรณ์ USB ควรระวังอะไรเพื่อป้องกันการโจมตีทางไซเบอร์?",
            "ตรวจสอบว่าอุปกรณ์ USB ไม่มีไวรัส",
            "เชื่อมต่ออุปกรณ์ USB ให้กับคอมพิวเตอร์ที่ไม่มีการเข้ารหัสข้อมูล",
            "ใช้ USB ที่หายากจะหาตามร้านค้า",
            "อนุญาตให้คอมพิวเตอร์อื่นเข้าถึงข้อมูลบนอุปกรณ์ USB",
            "คำตอบที่ถูกคือ 1"
        )
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
