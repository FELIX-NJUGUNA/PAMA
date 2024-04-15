@file:Suppress("DEPRECATION")

package com.example.pama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        val pama = findViewById<ImageView>(R.id.pama_logo)

        pama.alpha=0f
        pama.animate().setDuration(1500).alpha(1f).withEndAction{
            Intent(this,LoginActivity::class.java)

           overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }


    }


}