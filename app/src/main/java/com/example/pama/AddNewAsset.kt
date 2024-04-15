package com.example.pama

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.pama.databinding.FragmentAddNewAssetBinding

class AddNewAsset : Fragment() {
    // TODO: Rename and change types of parameters

    private var binding: FragmentAddNewAssetBinding? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       val rootview: View =  inflater.inflate(R.layout.fragment_add_new_asset, container, false)
        binding = FragmentAddNewAssetBinding.inflate(inflater, container, false)
        val btnCancel: Button = requireView().findViewById(R.id.cancel_button)
        val btnSave: Button = requireView().findViewById(R.id.save_asset)
        btnCancel.setOnClickListener {
            dismiss()
        }

        //saves to the database and also displays on recycler view
        btnSave.setOnClickListener {

        }



        return rootview

    }

    private fun dismiss() {
        requireActivity().supportFragmentManager.popBackStack()
    }



}