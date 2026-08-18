package com.develop.cybersecurise

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.conding.cybersecurise.services.AppSettings.Companion.isAccessibilityEnabled
import com.conding.cybersecurise.services.AppSettings.Companion.isDevelopmentSettingsEnabled
import com.conding.cybersecurise.services.AppSettings.Companion.isMockLocationEnabled
import com.conding.cybersecurise.services.AppSettings.Companion.isOverlayPermissionGranted
import com.conding.cybersecurise.services.AppSettings.Companion.isWifiWatchdogOn
import com.develop.cybersecurise.databinding.ActivityDisplayEnableSettingsBinding
import com.develop.cybersecurise.models.EnabledSettingModel
import com.develop.cybersecurise.services.AdapterSettingEnables
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class DisplayEnableSettings : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayEnableSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val settings:MutableList<EnabledSettingModel> = ArrayList()
        binding = ActivityDisplayEnableSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alertDialogBuilder = AlertDialog.Builder(this)

        settings.add(EnabledSettingModel(name = "ACCESSIBILITY_SERVICE", enabled = isAccessibilityEnabled(this)))
        settings.add(EnabledSettingModel(name = "ALLOW_MOCK_LOCATION", enabled = isMockLocationEnabled(this)))
        settings.add(EnabledSettingModel(name = "DEVELOPMENT_SETTINGS_ENABLED", enabled = isDevelopmentSettingsEnabled(this)))
        settings.add(EnabledSettingModel(name = "WIFI_WATCHDOG_ON", enabled = isWifiWatchdogOn(this)))
        settings.add(EnabledSettingModel(name = "OVERLAY_PERMISSION", enabled = isOverlayPermissionGranted(this)))

        binding.listView.isClickable = true
        val adapter = AdapterSettingEnables(this, settings, alertDialogBuilder)
        binding.listView.adapter = adapter


        binding.menuButton.setOnClickListener{
            val auths = FirebaseAuth.getInstance()
            val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
            val navView: NavigationView = findViewById(R.id.nav_view)

            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            if(auths.currentUser!=null){
                val nav_Header = navView.getHeaderView(0).findViewById<TextView>(R.id.user_email)
                nav_Header.text = auths.currentUser!!.email
            }else{
                val nav_Header = navView.getHeaderView(0).findViewById<TextView>(R.id.user_email)
                nav_Header.text = "โปรดล็อคอินเข้าสู่ระบบ"
            }
            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_scan_account -> {
                        startActivity(Intent(this, securiseAccountActivity::class.java))
                    }
                    R.id.nav_scan_phone -> {
                        startActivity(Intent(this, securisePhoneScamSpam::class.java))
                    }
                    R.id.nav_scan_SMS -> {
                        startActivity(Intent(this, ReadSMSActivity::class.java))
                    }
                    R.id.nav_settings -> {
                        startActivity(Intent(this, DisplayEnableSettings::class.java))
                    }
                    R.id.nav_appinstall -> {
                        startActivity(Intent(this, AppInstalledActivity::class.java))
                    }
                    R.id.nav_home -> {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    R.id.nav_tester -> {
                        startActivity(Intent(this, QuickCyberTest::class.java))
                    }
                    R.id.nav_culprit -> {
                        if (com.google.firebase.auth.FirebaseAuth.getInstance().currentUser != null) {
                            startActivity(Intent(this, ReportCenter::class.java))
                        } else {
                            Toast.makeText(this, "โปรด Login เข้าสู่ระบบก่อนใช้งานเมนู 'รายงานผู้ก่อนเหตุ'", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                true
            }
        }

    }
}