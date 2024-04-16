package com.example.pama

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pama.pamaDB.DatabaseHandler


class Accounts : Fragment() {

    private lateinit var databaseHandler: DatabaseHandler

    // Layout variables
    private lateinit var cashIncomeTextView: TextView
    private lateinit var cashExpenseTextView: TextView
    private lateinit var cashBalanceTextView: TextView

    private lateinit var bankIncomeTextView: TextView
    private lateinit var bankExpenseTextView: TextView
    private lateinit var bankBalanceTextView: TextView

    private lateinit var cardIncomeTextView: TextView
    private lateinit var cardExpenseTextView: TextView
    private lateinit var cardBalanceTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_accounts, container, false)

        // Initialize text views for each account
        cashIncomeTextView = view.findViewById(R.id.income_text_view_cash)
        cashExpenseTextView = view.findViewById(R.id.expense_text_view_cash)
        cashBalanceTextView = view.findViewById(R.id.balance_text_view_cash)

        bankIncomeTextView = view.findViewById(R.id.income_text_view_bank)
        bankExpenseTextView = view.findViewById(R.id.expense_text_view_bank)
        bankBalanceTextView = view.findViewById(R.id.balance_text_view_bank)

        cardIncomeTextView = view.findViewById(R.id.income_text_view_card)
        cardExpenseTextView = view.findViewById(R.id.expense_text_view_card)
        cardBalanceTextView = view.findViewById(R.id.balance_text_view_card)

        // Get database handler instance (assuming you have a way to get it)
        databaseHandler = DatabaseHandler(requireContext())

        // Call methods to fetch and display data
        fetchAccountData("Cash")
        fetchAccountData("Bank")
        fetchAccountData("Card")



        return view
    }


    @SuppressLint("SetTextI18n")
    private fun fetchAccountData(accountType: String) {
        val totalIncome = databaseHandler.getTotalIncomeByAccount(accountType)
        val totalExpense = databaseHandler.getTotalExpenseByAccount(accountType)
        val balance = totalIncome - totalExpense

        // Update text views with fetched data (assuming appropriate formatting)
        when (accountType) {
            "Cash" -> {
                cashIncomeTextView.text = "Ksh " + String.format("%.2f", totalIncome)
                cashExpenseTextView.text = "Ksh " + String.format("%.2f", totalExpense)
                cashBalanceTextView.text = "Ksh " + String.format("%.2f", balance)
            }
            "Bank" -> {
                bankIncomeTextView.text = "Ksh " + String.format("%.2f", totalIncome)
                bankExpenseTextView.text = "Ksh " + String.format("%.2f", totalExpense)
                bankBalanceTextView.text = "Ksh " + String.format("%.2f", balance)
            }
            "Card" -> {
                cardIncomeTextView.text = "Ksh " + String.format("%.2f", totalIncome)
                cardExpenseTextView.text = "Ksh " + String.format("%.2f", totalExpense)
                cardBalanceTextView.text = "Ksh " + String.format("%.2f", balance)
            }
        }
    }


}