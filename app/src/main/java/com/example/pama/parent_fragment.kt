package com.example.pama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pama.Accounts
import com.google.android.material.bottomnavigation.BottomNavigationView

private lateinit var bottomNavigationView: BottomNavigationView


class parent_fragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_fragment)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)


        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> run {
                    replaceFragment(Home())
                    true
                }

                R.id.account -> run {
                    replaceFragment(Accounts())
                    true
                }

                R.id.business -> {
                    replaceFragment(Businesses())
                    true
                }

                R.id.settings -> {
                    replaceFragment(Settings())
                    true
                }

                else -> false


            }
        }
        replaceFragment(Home())


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

}