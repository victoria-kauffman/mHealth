package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moh.mhealth.Global_Helper
import com.moh.mhealth.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val username: EditText
        val password: EditText
        username = findViewById<View>(R.id.main_username) as EditText
        password = findViewById<View>(R.id.main_password) as EditText
        val loginBtn = findViewById<View>(R.id.main_login_button) as Button
        loginBtn.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()
            if (user == Global_Helper.USERNAME && pass == Global_Helper.PASSWORD) {
                Toast.makeText(this@MainActivity, "Welcome", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, PatientList::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}