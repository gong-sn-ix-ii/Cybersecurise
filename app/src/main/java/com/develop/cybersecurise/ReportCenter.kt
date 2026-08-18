package com.develop.cybersecurise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.develop.cybersecurise.databinding.ActivityReportCenterBinding
import com.develop.cybersecurise.models.ReportFirebaseModel
import com.develop.cybersecurise.services.SystemService
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import java.time.LocalDate

class ReportCenter : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityReportCenterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReportCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        database = Firebase.database.reference
        val user_report: String? = "Anonymous"

        binding.reportSubmit.setOnClickListener {

            val report = ReportFirebaseModel(
                user_report = user_report,
                bank_number = "${binding.bankNumberCulprit.text}",
                date = "${LocalDate.now()}",
                name = "${binding.nameCulprit.text}",
                phone = "${binding.phoneCulprit.text}",
                description = "${binding.descriptCulprit.text}",
            )

            database.child("report").child(SystemService().generateRandomString(20)).setValue(report)
                .addOnSuccessListener {
                    Toast.makeText(this, "add data to firebase success ${binding.descriptCulprit.text.toString()}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "add data to firebase  failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            binding.menuButton.setOnClickListener {
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