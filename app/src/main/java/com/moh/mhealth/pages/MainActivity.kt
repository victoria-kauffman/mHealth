package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moh.mhealth.GlobalHelper
import com.moh.mhealth.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val username: EditText = findViewById<View>(R.id.main_username) as EditText
        val password: EditText = findViewById<View>(R.id.main_password) as EditText
        val loginBtn = findViewById<View>(R.id.main_login_button) as Button
        loginBtn.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()
            if (user == GlobalHelper.USERNAME && pass == GlobalHelper.PASSWORD) {
                Toast.makeText(this@MainActivity, "Welcome", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, PatientList::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}