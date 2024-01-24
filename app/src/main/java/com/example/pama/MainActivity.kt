package com.example.pama


import android.content.Intent
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {


    private lateinit var registerbtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerbtn = findViewById(R.id.registerbtn)

        registerbtn.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)

        }


    }
}