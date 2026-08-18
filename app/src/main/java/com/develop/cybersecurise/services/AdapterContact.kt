package com.develop.cybersecurise.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.develop.cybersecurise.R
import com.develop.cybersecurise.models.ContactData
import de.hdodenhof.circleimageview.CircleImageView

class AdapterContact(private val context: Context, private val contactList:List<ContactData> ) :
    ArrayAdapter<ContactData>(context, R.layout.list_item_contact, contactList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item_contact, parent, false)
            holder = ViewHolder()
            holder.nameContact = view.findViewById(R.id.name_contact)
            holder.phoneContact = view.findViewById(R.id.phone_contact)
            holder.internationalCode = view.findViewById(R.id.internationalCode)
            holder.profileimage = view.findViewById(R.id.profile_image)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val currentData = contactList[position]
        holder.nameContact.text =    currentData.name
        holder.phoneContact.text = currentData.phoneNumber
        holder.profileimage.setImageBitmap(currentData.imageResourceId)
        holder.internationalCode.text = currentData.internationalPhoneNumber

        return view!!
    }

    private class ViewHolder {
        lateinit var nameContact: TextView
        lateinit var phoneContact: TextView
        lateinit var internationalCode: TextView
        lateinit var profileimage: CircleImageView
    }
}