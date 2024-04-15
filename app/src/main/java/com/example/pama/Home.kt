package com.example.pama

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pama.R.layout
import com.example.pama.pamaDB.DatabaseHandler

class Home : Fragment() {


    private lateinit var databaseHandler: DatabaseHandler


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(layout.fragment_home, container, false)

        databaseHandler = DatabaseHandler(requireContext())

        // Get the balances for each wallet type
        val cashBalance = databaseHandler.getBalanceByWalletType("cash")
        val cardBalance = databaseHandler.getBalanceByWalletType("card")
        val bankBalance = databaseHandler.getBalanceByWalletType("bank")

        // Calculate the net worth
        val netWorth = cashBalance + cardBalance + bankBalance

        // Get the total income and expense for each wallet type
        val cashIncome = databaseHandler.getTotalIncomeByAccount("cash")
        val cardIncome = databaseHandler.getTotalIncomeByAccount("card")
        val bankIncome = databaseHandler.getTotalIncomeByAccount("bank")
        val totalIncome = cashIncome + cardIncome + bankIncome

        val cashExpense = databaseHandler.getTotalExpenseByAccount("cash")
        val cardExpense = databaseHandler.getTotalExpenseByAccount("card")
        val bankExpense = databaseHandler.getTotalExpenseByAccount("bank")
        val totalExpense = cashExpense + cardExpense + bankExpense

        // Display the net worth, total income, and total expense in the UI
        view.findViewById<TextView>(R.id.networth_value).text = "Ksh. ${"%.2f".format(netWorth)}"
        view.findViewById<TextView>(R.id.total_income_value).text = "Ksh. ${"%.2f".format(totalIncome)}"
        view.findViewById<TextView>(R.id.total_expense_value).text = "Ksh. ${"%.2f".format(totalExpense)}"

        return view
    }





}