package com.develop.cybersecurise.services

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.develop.cybersecurise.R
import com.develop.cybersecurise.models.ContactData
import com.develop.cybersecurise.models.callHistoryModel
import de.hdodenhof.circleimageview.CircleImageView

class AdapterCallHistory(private val context: Context, private val contactHistory:List<callHistoryModel>): ArrayAdapter<callHistoryModel>(context, R.layout.list_item_callhistory, contactHistory) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item_callhistory, parent, false)
            holder = ViewHolder()
            holder.nameContact = view.findViewById(R.id.name_contactHistory)
            holder.date = view.findViewById(R.id.date)
            holder.type = view.findViewById(R.id.type)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val currentData = contactHistory[position]
        if(currentData.name==null || currentData.name=="null" || currentData.name == ""){
            if(currentData.stack > 1){
                holder.nameContact.text = "${currentData.phoneNumber} (${currentData.stack})"
            }else{
                holder.nameContact.text = "${currentData.phoneNumber}"
            }
        }else{
            if(currentData.stack > 1){
                holder.nameContact.text = "${currentData.name} (${currentData.stack})"
            }else{
                holder.nameContact.text = "${currentData.name}"
            }
        }

        //set color
        if (currentData.Type == "Missed"){
            holder.nameContact.setTextColor(Color.RED)
        }else{
            holder.nameContact.setTextColor(Color.BLACK)
        }

        holder.date.text = currentData.date
        holder.type.text = currentData.Type

        return view!!
    }

    private class ViewHolder {
        lateinit var nameContact: TextView
        lateinit var date: TextView
        lateinit var type: TextView
    }
}