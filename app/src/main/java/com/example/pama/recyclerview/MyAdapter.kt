package com.example.pama.recyclerview

import TransactionAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pama.BuinessView
import com.example.pama.R

private var Any.layoutManager: LinearLayoutManager
    get() { return layoutManager }
    set(value) {}
private var Any.adapter: TransactionAdapter
    get() {
        TODO("Not yet implemented")
    }
    set(value) {}

class MyAdapter(
    private var businesslist: List<Datalist>,
//    private val onItemDeleteClick: (Datalist) -> Unit
//    private val onItemEditClick: (Datalist) -> Unit
): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    private val transactionAdapter = TransactionAdapter(emptyList())
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val bname: TextView = itemView.findViewById(R.id.business_name)
        val cname: TextView = itemView.findViewById(R.id.category_name)
        val lname: TextView = itemView.findViewById(R.id.loaction_name)
//        val deleteButton: Button = itemView.findViewById(R.id.delbtn)
//        val editButton: Button = itemView.findViewById(R.id.editbtn)

        init {
//
//            deleteButton.setOnClickListener {
//                onItemDeleteClick(businesslist[adapterPosition])
//            }
//
////            editButton.setOnClickListener {
////                onItemEditClick(businesslist[adapterPosition])
////            }
            //make the recycler view clickable
            itemView.setOnClickListener {
                adapterPosition
                val activity = itemView.context as? AppCompatActivity
                val fragmentManager = activity?.supportFragmentManager
                val transaction = fragmentManager?.beginTransaction()
                val fragment = BuinessView()
                fragment.arguments = bundleOf()

                transaction?.replace(R.id.fragmentContainerView, fragment)
                transaction?.commit()

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.business_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return businesslist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentItem = businesslist[position]
        holder.bname.text = currentItem.businessname
        holder.cname.text = currentItem.category
        holder.lname.text = currentItem.location


//        // Set up the RecyclerView to display the transactions for this business
//        transactionAdapter.updateData(currentItem.transactions)
//        holder.itemView.tag = transactionAdapter

        // Set on click listener for the item view
        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as? AppCompatActivity
            val fragmentManager = activity?.supportFragmentManager
            val transaction = fragmentManager?.beginTransaction()
            val fragment = BuinessView()

            // Pass the selected business data to the BuinessView fragment
            val bundle = bundleOf("businessname" to currentItem.businessname, "category" to currentItem.category, "location" to currentItem.location)
            fragment.arguments = bundle

            transaction?.replace(R.id.fragmentContainerView, fragment)
            transaction?.commit()
        }

    }
}