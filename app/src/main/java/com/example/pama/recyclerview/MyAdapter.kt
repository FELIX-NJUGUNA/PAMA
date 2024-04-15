package com.example.pama.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.pama.BuinessView
import com.example.pama.R

class MyAdapter(
    private var businesslist: ArrayList<Datalist>,
//    private val onItemDeleteClick: (Datalist) -> Unit
//    private val onItemEditClick: (Datalist) -> Unit
): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

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

    }
}