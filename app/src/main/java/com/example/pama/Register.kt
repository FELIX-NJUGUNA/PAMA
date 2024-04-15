package com.example.pama

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pama.pamaDB.DatabaseHandler
import com.example.pama.pamaDB.Users
import org.mindrot.jbcrypt.BCrypt

class Register : AppCompatActivity() {


    private lateinit var username: EditText
    private lateinit var pass: EditText
    private lateinit var cpass: EditText
    private lateinit var regbtn: Button

    private lateinit var db: DatabaseHandler



    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        cpass = findViewById(R.id.confirm_password)
        db = DatabaseHandler(this)
        db.readableDatabase

        val loginbtn = findViewById<TextView>(R.id.loginbtn)
        regbtn = findViewById(R.id.btnregister)




        regbtn.setOnClickListener {
            val username = username.text.toString()
            val password = pass.text.toString()
            val conpassword = cpass.text.toString()



            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conpassword)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                if (password.length < 8) {
                    Toast.makeText(this, "Password should be a minimum of 8 characters", Toast.LENGTH_SHORT).show()
                } else if (!password.matches(".*[A-Z].*".toRegex())) {
                    Toast.makeText(this, "Password should contain at least one uppercase letter", Toast.LENGTH_SHORT).show()
                } else if (!password.matches(".*[a-z].*".toRegex())) {
                    Toast.makeText(this, "Password should contain at least one lowercase letter", Toast.LENGTH_SHORT).show()
                } else if (!password.matches(".*[!@#$%^&*()\\-__+.].*".toRegex())) {
                    Toast.makeText(this, "Password should contain at least one special character", Toast.LENGTH_SHORT).show()
                } else if (pass.text.toString() != cpass.text.toString()) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                } else {


                    if (db.checkUserExists(username)) {
                        Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                    }else {


                        if (password == conpassword) {

                            val hashedpassword = BCrypt.hashpw(password, BCrypt.gensalt())
                            val user = Users(
                                username = username,
                                password = hashedpassword,
                                string = null

                            )
                            val save = db.insertDataUsers(user)

                            if (save) {
                                Toast.makeText(
                                    this,
                                    "Registration successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "User Exists", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }


            //Go to login page
            loginbtn.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
    }

 }
