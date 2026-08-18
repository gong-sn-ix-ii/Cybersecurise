package com.develop.cybersecurise

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.develop.cybersecurise.databinding.ActivityLoginAndRegisBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Locale

class LoginAndRegis : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityLoginAndRegisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginAndRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.menuButton.setOnClickListener {
            val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
            val navView: NavigationView = findViewById(R.id.nav_view)

            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            if (auth.currentUser != null) {
                val nav_Header = navView.getHeaderView(0).findViewById<TextView>(R.id.user_email)
                val profile_none = navView.getHeaderView(0).findViewById<CircleImageView>(R.id.profile_user_none)
                val profile_have = navView.getHeaderView(0).findViewById<LinearLayout>(R.id.profile_user_have)
                val text_in_profile = navView.getHeaderView(0).findViewById<TextView>(R.id.text_in_profile)
                nav_Header.text = auth.currentUser!!.email
                profile_have.visibility = View.VISIBLE
                profile_none.visibility = View.GONE
                text_in_profile.text = auth.currentUser!!.email?.substring(0, 1)?.uppercase(Locale.ROOT)
            } else {
                val nav_Header = navView.getHeaderView(0).findViewById<TextView>(R.id.user_email)
                val profile_none = navView.getHeaderView(0).findViewById<CircleImageView>(R.id.profile_user_none)
                val profile_have = navView.getHeaderView(0).findViewById<LinearLayout>(R.id.profile_user_have)
                profile_have.visibility = View.GONE
                profile_none.visibility = View.VISIBLE
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
                        if (auth.currentUser != null) {
                            startActivity(Intent(this, ReportCenter::class.java))
                        } else {
                            Toast.makeText(this, "โปรด Login เข้าสู่ระบบก่อนใช้งานเมนู 'รายงานผู้ก่อนเหตุ'", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                true
            }
        }

        binding.navPathToLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        binding.navPathToRegister.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }
    }
}
