package com.example.pama

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


class Settings : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val logout = view.findViewById<Button>(R.id.logout)
        logout.setOnClickListener{

            val intent  = Intent(activity,LoginActivity::class.java)
            startActivity(intent)
        }



    return view
    }


    }




