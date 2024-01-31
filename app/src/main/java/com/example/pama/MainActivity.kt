package com.example.pama


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button





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