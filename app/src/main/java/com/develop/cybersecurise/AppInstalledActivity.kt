package com.develop.cybersecurise

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.develop.cybersecurise.databinding.ActivityAppInstalledBinding
import com.develop.cybersecurise.models.AppInstalledData
import com.develop.cybersecurise.services.AdapterAppInstalled
import com.develop.cybersecurise.services.SystemService
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class AppInstalledActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppInstalledBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAppInstalledBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val appInfo = getInstalledApps()
        showAppInstallDetail()
        binding.listview.isClickable = true
        val adapter = AdapterAppInstalled(this, appInfo)
        binding.listview.adapter = adapter
        binding.listview.setOnItemClickListener { parent, view, position, id ->
            val currentApp = appInfo!![position]

            val intent = Intent(this, AppInstalledDetails::class.java)

            intent.putExtra("label", currentApp?.label)
            intent.putExtra("permission", currentApp?.permission?.toTypedArray())

            startActivity(intent)
        }

        binding.menuBtn.setOnClickListener {
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

    private fun getInstalledApps(): List<AppInstalledData?> {
        val packageManager = packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val appInfoList = mutableListOf<AppInstalledData?>()
        var isFromPlayStore = false

        for (packageInfo in packages) {
            val packageName = packageInfo.packageName
            var isFromPlayStore = false
            if (packageName.contains("com.android.vending") || packageName.contains("com.android")
                || packageName.startsWith("com.transsion")|| packageName.startsWith("com.google.")) {

                isFromPlayStore = true
            }

            val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)

            val appName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
            val appIcon = packageInfo.applicationInfo.loadIcon(packageManager)

            if(!(appName.contains("com."))){
                if(packageName.contains("com.${appName.lowercase()}")) {
                    isFromPlayStore = true
                }else if(SystemService().scanAppNameInPlaystore(appName)){
                    isFromPlayStore = true
                }
            }else{
                continue
            }

            //End Script

            var score:Double = 0.0
            val appPermissions = getPermissionInfo(this, packageInfo.applicationInfo, appName)

            appPermissions.forEach { permission ->
                val riskLevel:Double = getPermissionRiskLevel(permission)
                score += riskLevel
            }

            val riskLevel:Double = (score/22.0)

            //49 - 64 = ความเสี่ยงปานกลาง | > 65 = อันตราย

            appInfoList.add(AppInstalledData(appIcon, appName, appPermissions, packageInfo.applicationInfo.enabled, isFromPlayStore, riskLevel))
        }

        return appInfoList
    }

    fun getPermissionRiskLevel(permission: String): Double {
        val permissions_LvMedium = listOf(
            "ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION",
            "CAMERA", "RECORD_AUDIO", "READ_CONTACTS",
            "READ_SMS", "RECEIVE_SMS", "READ_EXTERNAL_STORAGE",
            "WRITE_EXTERNAL_STORAGE", "BLUETOOTH_ADMIN", "SEND_SMS"
        )

        val permissions_LvHigh = listOf(
            "READ_PHONE_STATE", "CALL_PHONE", "WRITE_CONTACTS",
            "READ_CALL_LOG", "WRITE_CALL_LOG", "PROCESS_OUTGOING_CALLS",
            "ACCESS_BACKGROUND_LOCATION", "SYSTEM_ALERT_WINDOW", "USE_CREDENTIALS",
            "MODIFY_PHONE_STATE", "MANAGE_ACCOUNTS",
            //11
        )
        return when {
            permissions_LvHigh.contains(permission) -> 1.25
            permissions_LvMedium.contains(permission) -> 1.0
            else -> 0.0 // ไม่มีความเสี่ยงหรือไม่เข้าข่ายที่ต้องตรวจสอบ
        }
    }


    fun showAppInstallDetail(){
        val manager:PackageManager = this.packageManager
        val packageB:List<PackageInfo> = manager.getInstalledPackages(1)

        for (appInfo in packageB) {
            val appX:ApplicationInfo = appInfo.applicationInfo
            var isSystemApp:Boolean = ((appX.flags and ApplicationInfo.FLAG_SYSTEM) != 0)

//            if(isSystemApp==false){
////                Log.i("System Check Applications", appInfo.packageName + " |  IsSystemApp = ${isSystemApp}")
//            }

        }
    }

    fun getPermissionInfo(context: Context, appInfo: ApplicationInfo, appName: String): MutableList<String> {
        val appPermissions = mutableListOf<String>()
        try {
            val packageInfo = context.packageManager.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS)
            val permissions = packageInfo.requestedPermissions
            permissions?.forEach { permission ->
                val permissionName = permission.substringAfterLast(".")
                Log.d("Permission", " $appName -> $permissionName")
                appPermissions.add(permissionName)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return appPermissions
    }


}