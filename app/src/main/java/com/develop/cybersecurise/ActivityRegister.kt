package com.develop.cybersecurise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.develop.cybersecurise.services.SystemService
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ActivityRegister : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val btn_login = findViewById<Button>(R.id.login)
        val btn_regis_accept = findViewById<Button>(R.id.regis_accept)
        val menuButton = findViewById<ImageButton>(R.id.menuButton)

        val usernameText = findViewById<EditText>(R.id.regis_username)
        val passText = findViewById<EditText>(R.id.regis_pass)
        val confpassText = findViewById<EditText>(R.id.regis_confpass)
        val emailText = findViewById<EditText>(R.id.regis_email)

        // set firebase instance
        auth = Firebase.auth
        database = Firebase.database.reference
        val auths = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            Toast.makeText(this, "Login User", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btn_regis_accept.setOnClickListener {

            val email = emailText.text.toString()
            val password = passText.text.toString()
            val confirmPassword = confpassText.text.toString()
            val regisUsername = usernameText.text.toString()

            if (password == confirmPassword && email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            Log.d("Register To Firebase Account", "createUserWithEmail:success | UID ${user?.uid}")
                            Toast.makeText(
                                baseContext,
                                "Registration successful.",
                                Toast.LENGTH_SHORT
                            ).show()

                            database.child("users").child(user?.uid.toString()).setValue(mapOf(
                                "email" to email,
                                "password" to password,
                                "username" to regisUsername
                            ))
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Add data to Firebase success: $email", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Add data to Firebase failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Register To Firebase Account", "createUserWithEmail:failure", task.exception)
                            when (task.exception) {
                                is FirebaseAuthUserCollisionException -> {
                                    Toast.makeText(
                                        baseContext,
                                        "Email address is already in use.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                is FirebaseAuthInvalidCredentialsException -> {
                                    Toast.makeText(
                                        baseContext,
                                        "Invalid email address or password format.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else -> {
                                    Toast.makeText(
                                        baseContext,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
            } else {
                Toast.makeText(
                    baseContext,
                    "Passwords do not match or fields are empty.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        menuButton.setOnClickListener {
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
