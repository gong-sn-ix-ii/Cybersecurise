package com.develop.cybersecurise.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.develop.cybersecurise.R
import com.develop.cybersecurise.models.QuestionCyberData

class AdapterCyberTest(
    private val context: Context,
    private val cyberTesting: List<QuestionCyberData>
) : ArrayAdapter<QuestionCyberData>(context, R.layout.item_cyber_test, cyberTesting) {

    private var submitted = false

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            val inflater: LayoutInflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_cyber_test, parent, false)
            holder = ViewHolder()
            holder.title = view.findViewById(R.id.title_cyber)
            holder.firstChoice = view.findViewById(R.id.first_choice_btn)
            holder.secondChoice = view.findViewById(R.id.second_choice_btn)
            holder.threeChoice = view.findViewById(R.id.third_choice_btn)
            holder.fourChoice = view.findViewById(R.id.four_choice_btn)
            holder.answerText = view.findViewById(R.id.answerText)
            holder.answerIndicator = view.findViewById(R.id.answerIndicator)
            holder.showAnswer = view.findViewById(R.id.showAnswer)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val cyber = cyberTesting[position]
        holder.title.text = cyber.title
        holder.firstChoice.text = cyber.firstQuest
        holder.secondChoice.text = cyber.secondQuest
        holder.threeChoice.text = cyber.threeQuest
        holder.fourChoice.text = cyber.fourQuest

        // Set OnClickListener for each CheckBox
        setCheckBoxClickListener(holder.firstChoice, holder, position)
        setCheckBoxClickListener(holder.secondChoice, holder, position)
        setCheckBoxClickListener(holder.threeChoice, holder, position)
        setCheckBoxClickListener(holder.fourChoice, holder, position)

        // Set visibility of showAnswer based on submission status
        if (submitted) {
            holder.showAnswer.visibility = View.VISIBLE
            if (cyber.userAnswer == cyber.correctAnswer) {
                holder.answerIndicator.setImageResource(R.drawable.baseline_check_24)
                holder.showAnswer.setBackgroundColor(ContextCompat.getColor(context, R.color.Green))
            } else {
                holder.answerIndicator.setImageResource(R.drawable.baseline_close_24)
                holder.showAnswer.setBackgroundColor(ContextCompat.getColor(context, R.color.Red))
            }
            holder.answerText.text = "คำตอบที่ถูก: ${cyber.correctAnswer}"
        } else {
            holder.showAnswer.visibility = View.GONE
        }

        return view!!
    }

    private fun setCheckBoxClickListener(checkBox: RadioButton, holder: ViewHolder, position: Int) {
        checkBox.setOnClickListener {
            // Clear all checkboxes first
            clearAllCheckBoxes(holder)
            // Then check the clicked checkbox
            checkBox.isChecked = true
            // Update userAnswer in QuestionCyberData
            cyberTesting[position].userAnswer = when (checkBox) {
                holder.firstChoice -> 1
                holder.secondChoice -> 2
                holder.threeChoice -> 3
                holder.fourChoice -> 4
                else -> 0
            }
        }
    }

    private fun clearAllCheckBoxes(holder: ViewHolder) {
        holder.firstChoice.isChecked = false
        holder.secondChoice.isChecked = false
        holder.threeChoice.isChecked = false
        holder.fourChoice.isChecked = false
    }

    fun submitAnswers() {
        submitted = true
        notifyDataSetChanged()
    }

    private class ViewHolder {
        lateinit var title: TextView
        lateinit var firstChoice: RadioButton
        lateinit var secondChoice: RadioButton
        lateinit var threeChoice: RadioButton
        lateinit var fourChoice: RadioButton
        lateinit var answerIndicator: ImageView
        lateinit var answerText: TextView
        lateinit var showAnswer: ConstraintLayout
    }
}

