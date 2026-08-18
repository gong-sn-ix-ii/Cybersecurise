package com.develop.cybersecurise.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.develop.cybersecurise.R
import com.develop.cybersecurise.models.ScammerData

class AdapterCheckgon(private val context: Context, private val ScammerDataList: List<ScammerData?>?): ArrayAdapter<ScammerData>(context, R.layout.item_list_checkgon,
    ScammerDataList!!
){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            val inflater: LayoutInflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_list_checkgon, null)
            holder = ViewHolder()
            holder.bank_to_abbr = view.findViewById(R.id.title_bank_to_abbr)
            holder.bank_to_acct = view.findViewById(R.id.title_bank_to_acct)
            holder.createdTime = view.findViewById(R.id.title_createdTime)
            holder.dataSource = view.findViewById(R.id.title_dataSource)
            holder.status = view.findViewById(R.id.title_status)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val scammerData = ScammerDataList?.get(position)

        holder.bank_to_abbr.text = "ธนาคารที่โกง  =  ${scammerData?.bank_to_abbr ?: ""}"
        holder.bank_to_acct.text = "บัญชีผู้โกง  =  ${scammerData?.bank_to_acct ?: ""}"
        holder.createdTime.text = "เวลาทำการ  =  ${scammerData?.createdTime ?: ""}"
        holder.dataSource.text = "แหล่งข้อมูล  =  ${scammerData?.dataSource ?: ""}"
        holder.status.text = "สถานะ  =  ${scammerData?.status ?: ""}"

        return view!!
    }

    private class ViewHolder {
        lateinit var bank_to_abbr: TextView
        lateinit var bank_to_acct: TextView
        lateinit var createdTime: TextView
        lateinit var dataSource: TextView
        lateinit var status: TextView
    }
}