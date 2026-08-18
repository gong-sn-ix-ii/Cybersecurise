package com.example.quizapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.develop.cybersecurise.R
import org.w3c.dom.Text

class QuestionAdapter(private val context: Context, private val questions: List<List<String>>) : BaseAdapter() {

    private val selectedAnswers = IntArray(questions.size) { -1 }
    private val correctAnswers = BooleanArray(questions.size) { false }
    private val answered = BooleanArray(questions.size) { false }

    override fun getCount(): Int = questions.size

    override fun getItem(position: Int): Any = questions[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_question, parent, false)

        val questionText: TextView = view.findViewById(R.id.questionText)
        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val radioButton1: RadioButton = view.findViewById(R.id.radioButton1)
        val radioButton2: RadioButton = view.findViewById(R.id.radioButton2)
        val radioButton3: RadioButton = view.findViewById(R.id.radioButton3)
        val radioButton4: RadioButton = view.findViewById(R.id.radioButton4)
        val linearShowAnswer: LinearLayout = view.findViewById(R.id.show_answer)
        val icon_answer: ImageView = view.findViewById(R.id.icon_for_correct_and_wrong)
        val textAnswer: TextView = view.findViewById(R.id.text_answer)

        val question = questions[position]

        questionText.text = question[0]
        radioButton1.text = question[1]
        radioButton2.text = question[2]
        radioButton3.text = question[3]
        radioButton4.text = question[4]

        radioGroup.setOnCheckedChangeListener(null)
        radioGroup.clearCheck()
        if (selectedAnswers[position] != -1) {
            (radioGroup.getChildAt(selectedAnswers[position]) as RadioButton).isChecked = true
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectedAnswers[position] = group.indexOfChild(view.findViewById(checkedId))
        }

        if (answered[position]) {
            val correctAnswerIndex = question[5].split(" ").last().toInt() - 1
            val userAnswerIndex = selectedAnswers[position]
            for (i in 0 until radioGroup.childCount) {
                val radioButton = radioGroup.getChildAt(i) as RadioButton
                radioButton.setBackgroundColor(Color.TRANSPARENT)
            }
            if (userAnswerIndex == correctAnswerIndex) {
                linearShowAnswer.visibility = View.VISIBLE
                icon_answer.setImageResource(R.drawable.icon_correct_answer)
                textAnswer.text = "คำตอบคือ ${correctAnswerIndex+1} ${question[correctAnswerIndex+1]}"
//                (radioGroup.getChildAt(userAnswerIndex) as RadioButton).setBackgroundColor(Color.GREEN)
            } else {
                linearShowAnswer.visibility = View.VISIBLE
                icon_answer.setImageResource(R.drawable.icon_wrong_answer)
                textAnswer.text = "คำตอบคือ ${correctAnswerIndex+1} ${question[correctAnswerIndex+1]}"
//                (radioGroup.getChildAt(userAnswerIndex) as RadioButton).setBackgroundColor(Color.RED)
//                (radioGroup.getChildAt(correctAnswerIndex) as RadioButton).setBackgroundColor(Color.GREEN)
            }
        }

        return view
    }

    fun checkAnswers(): Boolean {
        for (i in questions.indices) {
            if (selectedAnswers[i] == -1) {
                return false
            }
        }
        return true
    }

    fun setAnswered(position: Int) {
        answered[position] = true
    }
}
