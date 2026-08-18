package com.develop.cybersecurise.services

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.transition.Visibility
import com.develop.cybersecurise.R
import com.develop.cybersecurise.models.CardData
import com.develop.cybersecurise.models.EnabledSettingModel

class AdapterSettingEnables(
    private val context: Context,
    private val settings: List<EnabledSettingModel>,
    private val alertDialogBuilder: AlertDialog.Builder  // ต้องระบุตัวแปรนี้ให้ถูกต้อง
) : ArrayAdapter<EnabledSettingModel>(context, R.layout.item_setting_enabled, settings) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_setting_enabled, parent, false)
            holder = ViewHolder()
            holder.nameSettings = view.findViewById(R.id.name_setting)
            holder.iconAlert = view.findViewById(R.id.spamAlert)
            holder.switchEnabled = view.findViewById(R.id.switchEnabled)

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val setting = settings[position]

        holder.nameSettings.text = setting.name
        holder.switchEnabled.isChecked = setting.enabled

        holder.iconAlert.visibility = if (setting.enabled) View.VISIBLE else View.GONE

        view?.setOnClickListener {
//            Toast.makeText(context, "${setting.name} has ${setting.enabled}", Toast.LENGTH_SHORT).show()
            Log.d("AdapterSettingEnables", "${setting.name} clicked at position $position")
            var description:String = ""
            if(setting.name=="ACCESSIBILITY_SERVICE"){
                description += "การเปิดใช้งานบริการ Accessibility Service สามารถทำให้แอปพลิเคชันสามารถเข้าถึงข้อมูลส่วนตัวของผู้ใช้ได้โดยไม่ได้รับอนุญาต ซึ่งอาจเป็นที่ยอมรับได้หรือไม่ขึ้นอยู่กับเชิงมาตรฐานและวัตถุประสงค์ของการใช้งาน การให้สิทธิ์ในการเข้าถึงนี้ถือเป็นเรื่องที่ต้องระมัดระวังเนื่องจากความเสี่ยงที่อาจเกิดขึ้นเมื่อข้อมูลส่วนตัวถูกเปิดเผยได้โดยไม่ได้คาดคะเน การอนุญาตให้ใช้งานบริการนี้ควรพิจารณาถึงการรักษาความปลอดภัยของข้อมูลที่ถูกเข้าถึงด้วย"
            }else if(setting.name=="ALLOW_MOCK_LOCATION"){
                description += "การอนุญาตให้ใช้งานตำแหน่งจำลอง (Mock Location) สามารถทำให้แอปพลิเคชันสามารถปลอมตำแหน่งที่ตั้งของอุปกรณ์ได้ ซึ่งอาจถูกใช้เพื่อการปลอมแปลงข้อมูลที่เกี่ยวข้องกับตำแหน่งที่ตั้ง การใช้งานนี้อาจนำไปสู่การละเมิดความเป็นส่วนตัวหรือการประมวลผลข้อมูลที่ไม่ถูกต้อง"
            }else if(setting.name=="DEVELOPMENT_SETTINGS_ENABLED"){
                description += "การเปิดใช้งานการตั้งค่าสำหรับนักพัฒนา (Development Settings) สามารถทำให้ผู้ใช้ที่มีความรู้ความสามารถในการพัฒนาแอปพลิเคชันสามารถเข้าถึงตัวเลือกที่ไม่ปกติหรือทำให้เปิดใช้งานฟังก์ชันที่อาจเสี่ยงต่อความปลอดภัยของอุปกรณ์ การให้สิทธิ์ในการเข้าถึงนี้อาจเป็นที่ยอมรับได้หรือไม่ขึ้นอยู่กับวัตถุประสงค์และเชิงมาตรฐานของการใช้งาน"
            }else if(setting.name=="WIFI_WATCHDOG_ON"){
                description += "การเปิดใช้งาน WiFi Watchdog สามารถทำให้ระบบตรวจจับการเชื่อมต่อ WiFi ที่ไม่เป็นไปตามปกติและทำการปรับปรุงให้เป็นไปตามที่กำหนดได้ ซึ่งอาจทำให้เกิดความไม่ปลอดภัยต่อเครือข่าย WiFi หรือการเข้าถึงข้อมูลอื่นที่อาจเกี่ยวข้องได้"
            }else if(setting.name=="OVERLAY_PERMISSION"){
                description += "การให้สิทธิ์ในการใช้งานลายกาบนหน้าจอ (Overlay Permission) สามารถทำให้แอปพลิเคชันสามารถแสดงภาพซ้อนหน้าจอที่ไม่ได้ขออนุญาตล่วงหน้าได้ ซึ่งอาจทำให้เกิดการซ่อนข้อมูลหรือการกระทำที่ไม่เหมาะสมบนหน้าจอได้"
            }else{
                description += "ไม่มีคำอธิบาย"
            }


            showDialog( setting.name, description, context)

        }

        return view!!
    }

    private class ViewHolder {
        lateinit var nameSettings: TextView
        lateinit var iconAlert: ImageView
        lateinit var switchEnabled: SwitchCompat
    }

    private fun showDialog(name_setting: String, descript: String, context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        val alertDialog = alertDialogBuilder.create()
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_layout_have_btn, null)
        alertDialog.setView(view)

        val image = view.findViewById<ImageView>(R.id.image)
        val detail = view.findViewById<TextView>(R.id.detail)
        val btn_setting = view.findViewById<Button>(R.id.btn_open_settings)

        image.setImageResource(R.drawable.facebook_fake_logo)
        detail.text = "${name_setting}\n\n${descript}"

        btn_setting.setOnClickListener {
            when (name_setting) {
                "ACCESSIBILITY_SERVICE" -> {
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    context.startActivity(intent)
                }
                "ALLOW_MOCK_LOCATION" -> {
                    // Intent for mock location settings, customize as needed
                    val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
                    context.startActivity(intent)
                }
                "DEVELOPMENT_SETTINGS_ENABLED" -> {
                    // Intent for development settings, customize as needed
                    val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
                    context.startActivity(intent)
                }
                "WIFI_WATCHDOG_ON" -> {
                    // Intent for WiFi settings, customize as needed
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    context.startActivity(intent)
                }
                "OVERLAY_PERMISSION" -> {
                    // Intent for overlay permission settings, customize as needed
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    context.startActivity(intent)
                }
            }
        }

        alertDialog.show()
    }

}