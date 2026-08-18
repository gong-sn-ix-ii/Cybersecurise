package com.develop.cybersecurise.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.develop.cybersecurise.R
import com.develop.cybersecurise.models.AppInstalledData
import java.text.DecimalFormat

class AdapterAppInstalled(private val context: Context, private val appInfo: List<AppInstalledData?>?):ArrayAdapter<AppInstalledData>(context, R.layout.item_app_installed, appInfo!!){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if(view==null){
            val inflater:LayoutInflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_app_installed, parent , false)
            holder = ViewHolder()
            holder.appIcon = view.findViewById(R.id.app_icon)
            holder.appName = view.findViewById(R.id.app_name)
            holder.appEnabled = view.findViewById(R.id.app_subtitle)
            holder.isPlaystore = view.findViewById(R.id.app_isplaystore)
            view.tag = holder

        }else {
            holder = view.tag as ViewHolder
        }

        val currentApp = appInfo!![position]
        var settext: String = "NONE"
        var warningApp: String = "NONE"

        if(currentApp!!.isEnable){
            settext = "Enable"
            holder.appEnabled.setTextColor(ContextCompat.getColor(context, R.color.DarkGreen))
            if(currentApp!!.isPlaystore){
                warningApp = "ปลอดภัย"
                holder.isPlaystore.setTextColor(ContextCompat.getColor(context, R.color.Navy))
            }else{
                if((currentApp.riskLevel*100) > 54){
                    warningApp = "อาจเป็นอันตราย"
                    holder.isPlaystore.setTextColor(ContextCompat.getColor(context, R.color.Crimson))
                }else if((currentApp.riskLevel*100) > 24 && (currentApp.riskLevel*100) < 55){
                    warningApp = "เสี่ยงปานกลาง"
                    holder.isPlaystore.setTextColor(ContextCompat.getColor(context, R.color.Orange))
                }else{
                    warningApp = "ปลอดภัย"
                    holder.isPlaystore.setTextColor(ContextCompat.getColor(context, R.color.Navy))
                }
            }
        }else{
                settext = "Disable (อาจเป็นอันตราย)"
                warningApp = ""
                holder.appEnabled.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

        holder.appName.text = currentApp.label
        holder.appIcon.setImageDrawable(currentApp.icon)
        holder.appEnabled.text = settext
        val decimalFormat = DecimalFormat("#.##")
        val formattedString = decimalFormat.format((currentApp.riskLevel) * 100)
        holder.isPlaystore.text = warningApp + " (${formattedString}%)"

        return view!!
    }

    private class ViewHolder {
        lateinit var appIcon: ImageView
        lateinit var appName: TextView
        lateinit var appEnabled: TextView
        lateinit var isPlaystore: TextView
    }

}