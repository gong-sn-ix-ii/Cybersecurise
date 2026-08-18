package com.develop.cybersecurise.services

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.develop.cybersecurise.R
import com.develop.cybersecurise.models.SMSMessageData


class AdapterMessageSMS(context: Context, messageSMS: MutableMap<String, List<SMSMessageData>>) :
    ArrayAdapter<SMSMessageData>(context, R.layout.list_item_messagesms) {

    private val flattenedList: MutableList<SMSMessageData> = mutableListOf()

    init {
        // Flatten the map into a list
        messageSMS.values.forEach { messageList ->
            flattenedList.addAll(messageList)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item_messagesms, parent, false)
            holder = ViewHolder()
            holder.messageSMS = view.findViewById(R.id.messageSMS)
            holder.dateSMS = view.findViewById(R.id.dateTime)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val message = getItem(position)
        if (message!!.isBlocking){
            holder.messageSMS.text = "Blocked SMS"
            holder.messageSMS.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.messageSMS.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
            holder.messageSMS.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            holder.messageSMS.setBackgroundResource(R.drawable.bg_circle_black)
        }else{
            holder.messageSMS.text = message?.message
            holder.messageSMS.setTextColor(ContextCompat.getColor(context, R.color.black))
            holder.messageSMS.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
        }

        Log.d("Check Sender SMS and Message ALL", "$message")
        holder.dateSMS.text = SystemService().formatDateSMS(message!!.date)

        return view!!
    }

    override fun getCount(): Int {
        return flattenedList.size
    }

    override fun getItem(position: Int): SMSMessageData? {
        return flattenedList.getOrNull(position)
    }

    private class ViewHolder {
        lateinit var messageSMS: TextView
        lateinit var dateSMS: TextView
    }
}