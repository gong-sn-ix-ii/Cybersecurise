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

class CyberExam2 : AppCompatActivity() {

    private lateinit var binding: ActivityCyberExam1Binding

    val questions = listOf(
        listOf(
            "1. เมื่อคุณได้รับอีเมลจากคนที่ไม่รู้จักและมีลิงก์ภายใน ควรทำอย่างไร?",
            "คลิกลิงก์เพื่อดูว่ามันคืออะไร",
            "ลบอีเมลทันทีโดยไม่คลิกที่ลิงก์",
            "ตอบกลับเพื่อถามว่ามันคืออะไร",
            "เปิดอีเมลใหม่เพื่อตรวจสอบ",
            "คำตอบที่ถูกคือ 2"
        ),
        listOf(
            "2. การใช้รหัสผ่านที่มีความปลอดภัยควรมีลักษณะอย่างไร?",
            "มีความยาวอย่างน้อย 8 ตัวอักษร",
            "ใช้เฉพาะตัวเลข",
            "ประกอบด้วยตัวอักษรพิมพ์ใหญ่และเล็ก",
            "เป็นคำพูดที่คุ้นเคย",
            "คำตอบที่ถูกคือ 3"
        ),
        listOf(
            "3. หากคุณต้องการตรวจสอบความปลอดภัยของเว็บไซต์ คุณควรดูข้อมูลใด?",
            "ประวัติการเข้าชมของเว็บไซต์",
            "ประวัติการโดเมนของเว็บไซต์",
            "สัญลักษณ์ SSL ที่ใช้ใน URL",
            "สีพื้นหลังของเว็บไซต์",
            "คำตอบที่ถูกคือ 3"
        ),
        listOf(
            "4. เมื่อคุณใช้ Wi-Fi สาธารณะ ควรปฏิบัติอย่างไรเพื่อป้องกันการโจมตีทางไซเบอร์?",
            "ใช้ VPN",
            "เชื่อมต่อ Wi-Fi และไม่ทำอะไรเพิ่ม",
            "ใช้รหัสผ่านแรง",
            "เชื่อมต่อ Wi-Fi และส่งข้อมูลเชิงส่วนตัว",
            "คำตอบที่ถูกคือ 1"
        ),
        listOf(
            "5. การป้องกันการโจมตีทางไซเบอร์เบื้องต้นควรทำอย่างไร?",
            "อัพเดตซอฟต์แวร์ประจำทั้งเครื่อง",
            "ปิดการเชื่อมต่ออินเทอร์เน็ต",
            "เปิดเผยข้อมูลส่วนตัว",
            "เปิดไฟล์จากแหล่งที่ไม่น่าเชื่อถือ",
            "คำตอบที่ถูกคือ 1"
        ),
        listOf(
            "6. เมื่อคุณเข้าสู่เว็บไซต์ที่ต้องการให้คุณกรอกข้อมูลบัญชีธนาคาร ควรตรวจสอบอะไร?",
            "ข้อมูลที่ให้มาตรงกับ URL ของเว็บไซต์",
            "รายละเอียดของผู้ส่ง",
            "รายละเอียดของผู้รับ",
            "ข้อความที่มีการยืนยันตัวตน 2 ชั้น",
            "คำตอบที่ถูกคือ 1"
        ),
        listOf(
            "7. การทำสำเนาข้อมูลสำคัญอย่างไรจึงจะปลอดภัย?",
            "บันทึกสำเนาไว้ใน USB แบบไม่มีการเข้ารหัส",
            "ส่งสำเนาผ่านทางอีเมลทั่วไป",
            "บันทึกสำเนาไว้ในบริการ Cloud Storage ที่มีการเข้ารหัสข้อมูล",
            "นำสำเนาไปใช้ในเครื่องสแกนเนอร์ที่ไม่มีความปลอดภัย",
            "คำตอบที่ถูกคือ 3"
        ),
        listOf(
            "8. เมื่อคุณพบว่าโทรศัพท์มือถือของคุณได้รับการแฮ็ก ควรทำอย่างไร?",
            "ปลดล็อคเครื่องและใช้งานต่อไปเหมือนเดิม",
            "ติดต่อผู้ให้บริการโทรศัพท์มือถือ",
            "ลบแอปพลิเคชันที่คิดว่าเป็นเหตุผล",
            "ลบข้อมูลส่วนตัวทั้งหมดและกู้คืนข้อมูล",
            "คำตอบที่ถูกคือ 4"
        ),
        listOf(
            "9. ควรทำอย่างไรเมื่อคุณต้องการทราบว่าไซเบอร์โจมตีกำลังเกิดขึ้น?",
            "ละเมิดข้อกำหนดในนโยบายระบบการใช้งาน",
            "อ่านข้อมูลข่าวสารจากแหล่งที่มีความน่าเชื่อถือ",
            "แชร์ข้อมูลเกี่ยวกับการโจมตีไซเบอร์",
            "ยกเลิกบัญชีของคุณทันที",
            "คำตอบที่ถูกคือ 2"
        ),
        listOf(
            "10. เมื่อคุณเริ่มใช้งานแอปพลิเคชันใหม่ ควรทำอย่างไรเพื่อป้องกันตัวจากภัยคุกคาม?",
            "เช็คความเป็นมัลแวร์ของแอปพลิเคชัน",
            "อ่านข้อมูลผู้ใช้บนเว็บไซต์ที่ไม่น่าเชื่อถือ",
            "ใช้คำพูดที่ไม่คุ้นเคย",
            "ติดตั้งแอปพลิเคชันที่ไม่มีการตรวจสอบความปลอดภัย",
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
