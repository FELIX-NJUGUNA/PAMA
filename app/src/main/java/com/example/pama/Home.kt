package com.example.pama

import TransactionAdapter
import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pama.R.layout
import com.example.pama.pamaDB.DatabaseHandler
import com.example.pama.recyclerview.TransactionDataList

class Home : Fragment() {

    private lateinit var db:DatabaseHandler
    private lateinit var newArr: ArrayList<TransactionDataList>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter



    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(layout.fragment_home, container, false)

        db = DatabaseHandler(requireContext())
        //handle welcome message
        val username = db.getUsername(requireContext())

        if (username != null) {
            view.findViewById<TextView>(R.id.username_welcome).text = "Welcome $username"
        } else {
            view.findViewById<TextView>(R.id.username_welcome).text = "Username: Not set"
        }




        //recycler view handling
        recyclerView = view.findViewById(R.id.transactions)

        newArr = ArrayList()
        adapter = TransactionAdapter(newArr)



        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TransactionAdapter(newArr)

        displayTransaction()

        // Get the balances for all wallet types
        val balances = db.getBalancesByWalletType()

        // Calculate the net worth
        var netWorth = 0.0
        for (balance in balances.values) {
            netWorth += balance
        }

        // Get the total income and expense for all wallet types
        val totalIncome = db.getTotalIncomeByAllAccounts()
        val totalExpense = db.getTotalExpenseByAllAccounts()

        // Display the net worth, total income, and total expense in the UI
        view.findViewById<TextView>(R.id.networth_value).text = "Ksh. ${"%.2f".format(netWorth)}"
        view.findViewById<TextView>(R.id.total_income_value).text = "Ksh. ${"%.2f".format(totalIncome)}"
        view.findViewById<TextView>(R.id.total_expense_value).text = "Ksh. ${"%.2f".format(totalExpense)}"


        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayTransaction() {
        val newcursor: Cursor? = db.getTransactions()
        newArr.clear()
        while (newcursor!!.moveToNext()){
            val spenton = newcursor.getString(1)
            val date = newcursor.getString(2)
            val amount = newcursor.getDouble(if(newcursor.getString(2) == "income") 5 else 6)
            val type = newcursor.getString(4)
            newArr.add(TransactionDataList(spenton,date,type,amount))
        }
        newcursor.close()
        recyclerView.adapter?.notifyDataSetChanged()
    }



}