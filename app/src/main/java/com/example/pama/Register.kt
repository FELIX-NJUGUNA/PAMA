package com.example.pama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Register : AppCompatActivity() {

    private lateinit var loginbtn: Button
    private lateinit var register: Register
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var conpass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loginbtn = findViewById(R.id.loginbtn)

        loginbtn.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}