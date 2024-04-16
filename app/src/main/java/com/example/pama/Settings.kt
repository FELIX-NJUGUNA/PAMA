package com.example.pama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pama.pamaDB.DatabaseHandler


class Settings : Fragment() {



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val databaseHandler = DatabaseHandler(requireContext())
        val username = databaseHandler.getUsername(requireContext())

        if (username != null) {
            view.findViewById<TextView>(R.id.settings_username).text = "Hello $username"
        } else {
            view.findViewById<TextView>(R.id.settings_username).text = "Username: Not set"
        }


        val logout = view.findViewById<Button>(R.id.logout)
        logout.setOnClickListener{

            val intent  = Intent(activity,LoginActivity::class.java)
            startActivity(intent)
        }



    return view
    }


    }




