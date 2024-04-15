package com.example.pama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pama.pamaDB.DatabaseHandler
import com.example.pama.pamaDB.LoginStatus

class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var pass: EditText
    private lateinit var logbtn: Button
    private lateinit var db: DatabaseHandler



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        val btnreg = findViewById<TextView>(R.id.registerbtn)
        logbtn = findViewById(R.id.loginbtn)
        db = DatabaseHandler(this)


        logbtn.setOnClickListener {
            val username = username.text.toString().trim()
            val password = pass.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter the username and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else {

                    val (isUserValid,_) = db.checkUserPass(username, password)

                    if (isUserValid == LoginStatus.SUCCESS) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT)
                            .show()
                    }

            }
        }

        btnreg.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}