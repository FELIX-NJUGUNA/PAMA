package com.example.pama

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pama.pamaDB.DatabaseHandler
import com.example.pama.recyclerview.Datalist
import com.example.pama.recyclerview.MyAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Businesses : Fragment() {

    private lateinit var db:DatabaseHandler
    private lateinit var newArry: ArrayList<Datalist>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_businesses, container, false)
        val fab : FloatingActionButton = view.findViewById(R.id.fab)



        recyclerView = view.findViewById(R.id.business_list )
        newArry = ArrayList()
        //add onEditClick Function next to onDeleteClick(later)
        adapter = MyAdapter(newArry)

         db = DatabaseHandler(requireContext())


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MyAdapter(newArry)

        displaybusiness()


        fab.setOnClickListener{
            showAddNewAssetDialog(requireContext())
        }


        return view


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun displaybusiness() {
        val newcursor: Cursor? = db.getBusiness()
        newArry.clear()

        if (newcursor != null) {

                while (newcursor.moveToNext()) {
                    val buname = newcursor.getString(1)
                    val catname = newcursor.getString(2)
                    val locname = newcursor.getString(3)

                    newArry.add(Datalist(buname, catname, locname))
                }

        }
        newcursor?.close()
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun showAddNewAssetDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.add_new_asset)
        dialog.setCancelable(true)// Allow user to cancel

        // pointers for adding new assets within the dialog
        val assetNameEditText: EditText = dialog.findViewById(R.id.assetname)
        val locationEditText: EditText = dialog.findViewById(R.id.location)
        val categoryEditText: EditText = dialog.findViewById(R.id.category)
        val savebtn: Button = dialog.findViewById(R.id.save_asset)
        val cancelbtn: Button = dialog.findViewById(R.id.cancel_button)
        val db = DatabaseHandler(context)


        cancelbtn.setOnClickListener {
            dialog.dismiss()
        }

        savebtn.setOnClickListener {
            //input saving logic using database
            val name = assetNameEditText.text.toString()
            val location = locationEditText.text.toString()
            val category =categoryEditText.text.toString()

            //Save data in the database
            db.insertDataBusiness(name,location,category)

            Toast.makeText(context, "Business saved successfully", Toast.LENGTH_SHORT).show()
            //close on saving successfully
            dialog.dismiss()
        }


        dialog.show()
    }

//    private fun onDeleteClick(item: Datalist) {
//        // Delete the item from your data source (e.g. a database) here
//        newArry.remove(item)
//        adapter.notifyItemRemoved(newArry.indexOf(item))
//    }

//    private fun onEditClick(item: Datalist) {
//        // Navigate to the edit screen for the clicked item here
//        findNavController().navigate(R.id.action_businesses_to_editBusinessFragment)
//    }

}

//review

