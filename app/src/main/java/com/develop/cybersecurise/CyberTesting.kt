package com.develop.cybersecurise

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.develop.cybersecurise.databinding.ActivityCyberTestingBinding
import com.develop.cybersecurise.models.QuestionCyberData
import com.develop.cybersecurise.services.AdapterCyberTest

class CyberTesting : AppCompatActivity() {

    private lateinit var binding: ActivityCyberTestingBinding
    private lateinit var questionList: List<QuestionCyberData>
    private lateinit var adapter: AdapterCyberTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCyberTestingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        var correctCount = 0

        questionList = listOf(
            QuestionCyberData(
                title = "1. การเข้ารหัสข้อมูลเพื่อป้องกันการโจรกรรมข้อมูลสำคัญมีความสำคัญอย่างไร?",
                firstQuest = "เพิ่มความเร็วในการส่งข้อมูล",
                secondQuest = "ลดการใช้งานพื้นที่เก็บข้อมูล",
                threeQuest = "ป้องกันการอ่านข้อมูลจากบุคคลที่ไม่มีสิทธิ์",
                fourQuest = "ประหยัดค่าใช้จ่ายในการบริหารจัดการข้อมูล",
                correctAnswer = 3
            ),
            QuestionCyberData(
                title = "2. การเลือกใช้พาสเวิร์ดที่ปลอดภัยสำหรับการเข้าสู่ระบบมีความสำคัญอย่างไร?",
                firstQuest = "ป้องกันการติดเชื้อไวรัส",
                secondQuest = "ป้องกันการโจรกรรมรหัสผ่าน",
                threeQuest = "ป้องกันการเข้าสู่ระบบโดยไม่ได้รับอนุญาต",
                fourQuest = "ลดความซับซ้อนในการบริหารจัดการระบบ",
                correctAnswer = 2
            ),
            QuestionCyberData(
                title = "3. การปฏิบัติตามนโยบายความมั่นคงปลอดภัยของข้อมูลมีผลต่อองค์กรอย่างไร?",
                firstQuest = "ช่วยในการบริหารจัดการความเสี่ยง",
                secondQuest = "ลดความรู้สึกปลอดภัยของผู้ใช้บริการ",
                threeQuest = "เพิ่มความเสี่ยงในการถูกโจรกรรม",
                fourQuest = "เพิ่มความซับซ้อนในการเข้าถึงข้อมูล",
                correctAnswer = 1
            ),
            QuestionCyberData(
                title = "4. การปฏิบัติตามหลักการ Least Privilege ส่งผลต่อความมั่นคงปลอดภัยอย่างไร?",
                firstQuest = "ลดความเสี่ยงในการถูกโจรกรรมข้อมูล",
                secondQuest = "ลดความเสี่ยงในการถูกโจรกรรมรหัสผ่าน",
                threeQuest = "ลดความเสี่ยงในการรั่วไหลข้อมูล",
                fourQuest = "ลดความเสี่ยงในการเข้าถึงข้อมูลโดยไม่ได้รับอนุญาต",
                correctAnswer = 4
            ),

            )

        adapter = AdapterCyberTest(this, questionList)
        binding.listviewCyber.adapter = adapter

        binding.submitBtn.setOnClickListener {
            val userAnswers = mutableListOf<Int>()
            correctCount = 0

            // เก็บคำตอบที่ผู้ใช้เลือกไว้ในรายการ userAnswers
            for (i in 0 until questionList.size) {
                val question = questionList[i]
                if (question.userAnswer != 0 && !userAnswers.contains(question.userAnswer)) {
                    userAnswers.add(question.userAnswer)
                }
            }

            // เปรียบเทียบคำตอบที่ผู้ใช้เลือกกับคำตอบที่ถูกต้อง
            for (i in 0 until questionList.size) {
                val question = questionList[i]
                if (userAnswers.contains(question.correctAnswer)) {
                    correctCount++
                }
            }

            // แสดงจำนวนคำตอบที่ถูกต้องใน Toast
//            Toast.makeText(this, "คุณตอบถูก $correctCount ข้อ", Toast.LENGTH_SHORT).show()

            // ส่งข้อมูลให้ Adapter ทำการแสดงผลคำตอบที่ถูกหลังจากผู้ใช้กด submit
            adapter.submitAnswers()
        }
    }
}



