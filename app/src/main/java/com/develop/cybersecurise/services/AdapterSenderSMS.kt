package com.develop.cybersecurise.services

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.develop.cybersecurise.R
import com.develop.cybersecurise.models.SMSMessageData
import com.google.type.Color

class AdapterSenderSMS(private val context: Context, private val allMessages: MutableMap<String, List<SMSMessageData>>) : BaseAdapter() {
    override fun getCount(): Int {
        return allMessages.size
    }

    override fun getItem(position: Int): Any {
        val sender = allMessages.keys.elementAt(position)
        return allMessages[sender]!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            val inflater: LayoutInflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_sms, parent, false)
            holder = ViewHolder()
            holder.messageTextView = view.findViewById(R.id.messageSMS)
            holder.senderTextView = view.findViewById(R.id.senderSMS)
            holder.spamAlert = view.findViewById(R.id.spamAlert)
            holder.dateTextView = view.findViewById(R.id.dateSMS)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val sender = allMessages.keys.elementAt(position)
        val messages = allMessages[sender]!!

        val latestMessage = messages.firstOrNull() // หาข้อความล่าสุด

//        if(latestMessage!!.isSpam){
        holder.messageTextView.text = latestMessage?.message ?: ""
        val statusText = latestMessage?.status?.toString() ?: "waiting"
        holder.senderTextView.text = (latestMessage?.sender ?: "")
        holder.dateTextView.text =  SystemService().formatDateSMS(latestMessage!!.date)
//        holder.spamAlert.visibility = View.GONE
        if(latestMessage.isBlocking){
            val drawable = ContextCompat.getDrawable(context, R.drawable.icon_block)
            holder.spamAlert.setImageDrawable(drawable)
            holder.senderTextView.setTextColor(ContextCompat.getColor(context, R.color.DarkRed))
            holder.messageTextView.setTextColor(ContextCompat.getColor(context, R.color.DarkRed))
        }else if(!latestMessage.isBlocking){
            holder.senderTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
            holder.messageTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
            if(statusText=="OK"){
                val drawable = ContextCompat.getDrawable(context, R.drawable.icon_okay_sphere)
                holder.spamAlert.setImageDrawable(drawable)
            }else if(statusText=="scam"){
                val drawable = ContextCompat.getDrawable(context, R.drawable.baseline_dangerous_24)
                holder.spamAlert.setImageDrawable(drawable)
            }else if(statusText=="OTP"){
                val drawable = ContextCompat.getDrawable(context, R.drawable.icons_otp)
                holder.spamAlert.setImageDrawable(drawable)
            }else if(statusText=="spam"){
                val drawable = ContextCompat.getDrawable(context, R.drawable.icon_spams)
                holder.spamAlert.setImageDrawable(drawable)
            }else if(statusText=="waiting"){
                val drawable = ContextCompat.getDrawable(context, R.drawable.icon_waiting)
                holder.spamAlert.setImageDrawable(drawable)
            }
        }



//        }else{
//            holder.messageTextView.text = latestMessage?.message ?: ""
//            holder.senderTextView.text = latestMessage?.sender ?: ""
//            holder.dateTextView.text =  SystemService().formatDateSMS(latestMessage!!.date)
//            holder.spamAlert.visibility = View.INVISIBLE
//        }

        return view!!
    }

    private class ViewHolder {
        lateinit var messageTextView: TextView
        lateinit var spamAlert: ImageView
        lateinit var senderTextView: TextView
        lateinit var dateTextView: TextView
    }
}