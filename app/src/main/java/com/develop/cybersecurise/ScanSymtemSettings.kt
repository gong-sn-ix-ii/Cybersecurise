package com.develop.cybersecurise

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class scanSystemSetting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scan_system_setting)

        val textView = TextView(this)

        var word: String = "null"
        // กำหนดข้อความเริ่มต้น
        textView.text = "Overlay permission status: "
        textView.gravity = Gravity.CENTER
        textView.textSize = 40f

        setContentView(textView)

        if(hasOverlayPermission(this)){
            textView.text = "Overlay has opening"
        }else {
            textView.text = "Overlay has closing"
        }

        if(isWifiEnabled(this)){
            Toast.makeText(this, "Wifi opening", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Wifi closing", Toast.LENGTH_LONG).show()
        }

    }

    fun hasOverlayPermission(context: Context): Boolean{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return Settings.canDrawOverlays(context)
        }
        return true
    }

    fun isWifiEnabled(context: Context): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return wifiManager.isWifiEnabled
        }
        return true
    }
}