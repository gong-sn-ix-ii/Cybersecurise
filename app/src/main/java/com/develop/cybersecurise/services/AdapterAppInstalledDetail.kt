package com.develop.cybersecurise.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.develop.cybersecurise.R

class AdapterAppInstalledDetail(private val context: Context, private val permissions: MutableList<String>) :
    ArrayAdapter<String>(context, R.layout.item_app_installed_detail, permissions) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_app_installed_detail, parent, false)
            holder = ViewHolder()
            holder.permissionName = view.findViewById(R.id.permissionName)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val currentPermission = permissions[position]
        holder.permissionName.text = currentPermission

        return view!!
    }

    class ViewHolder {
        lateinit var permissionName: TextView
    }
}
