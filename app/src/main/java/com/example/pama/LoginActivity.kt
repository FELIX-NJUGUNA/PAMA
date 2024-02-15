package com.example.pama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pama.pamaDB.DatabaseHandler


class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var pass: EditText
    private lateinit var logbtn: Button
    private lateinit var db : DatabaseHandler

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        val btnreg = findViewById<TextView>(R.id.registerbtn)
        logbtn = findViewById(R.id.loginbtn)
        db = DatabaseHandler(this)



        logbtn.setOnClickListener {


            val username = username.text.toString()
            val password = pass.text.toString()


            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {

                Toast.makeText(this, "Please enter the username and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else {
                val checkUser = db.checkUserPass(username, password)
                val existUser = db.userExists(username)
                if (!checkUser) {

                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()


                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }

//            //validate username and password in the database
//            val db = DatabaseHandler(this)
//            val user = db.findUserByUsername(username)
//
//
//            if(user != null){
//                // Login successful
//                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
//
//                // Navigate to the parent fragment
//
//                val intent = Intent(this, parent_fragment::class.java) // Example
//                startActivity(intent)
//                finish() // Close LoginActivity after successful login
//            } else {
//                // Login failed
//                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
//            }


        }

            btnreg.setOnClickListener {
                val intent = Intent(this, Register::class.java)
                startActivity(intent)
            }


        }
    }