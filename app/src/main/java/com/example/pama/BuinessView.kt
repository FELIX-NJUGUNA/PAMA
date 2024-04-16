    package com.example.pama



    import TransactionAdapter
import android.annotation.SuppressLint
import android.app.Dialog
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pama.pamaDB.DatabaseHandler
import com.example.pama.recyclerview.TransactionDataList
import java.text.SimpleDateFormat
import java.util.Calendar


    class BuinessView : Fragment() {

        private lateinit var db:DatabaseHandler
        private lateinit var newArr: ArrayList<TransactionDataList>
        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: TransactionAdapter


        //private lateinit var database: DatabaseReference

        @SuppressLint("MissingInflatedId")
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View? {
            // Inflate the layout for this fragment
           val view =  inflater.inflate(R.layout.fragment_buiness_view, container, false)

            //recycler view handling
            recyclerView = view.findViewById(R.id.transactions)

            newArr = ArrayList()
            adapter = TransactionAdapter(newArr)

            db = DatabaseHandler(requireContext())

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = TransactionAdapter(newArr)

            displayTransaction()


            //pass data from business  to business view
            // Get the passed data from the arguments bundle
            val businessname = arguments?.getString("businessname")
            val category = arguments?.getString("category")
            val location = arguments?.getString("location")

            // Display the passed data in the UI
            view.findViewById<TextView>(R.id.bis_name).text = businessname
            view.findViewById<TextView>(R.id.cat_name).text = category
            view.findViewById<TextView>(R.id.loc_name).text = location



            //nested scroll view
            val addbtn = view.findViewById<Button>(R.id.addTransaction)


//            val btnback = view.findViewById<Button>(R.id.back_btn)
//
//            btnback.setOnClickListener {
//                findNavController().navigateUp()
//          }

            addbtn.setOnClickListener{

                displayAddTransactionDialog()

            }



            return view
        }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayAddTransactionDialog(){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.add_transactions)
        dialog.setCancelable(true) // Allow user to cancel

        val transtitle: EditText = dialog.findViewById(R.id.transaction_title)
        val date: EditText = dialog.findViewById(R.id.date)
        val datepicker: DatePicker  = dialog.findViewById(R.id.datePicker)
        val wal_cash: RadioButton = dialog.findViewById(R.id.wal_cash)
        val wal_bank: RadioButton = dialog.findViewById(R.id.wal_bank)
        val wal_card: RadioButton = dialog.findViewById(R.id.wal_card)
        val amount: EditText = dialog.findViewById(R.id.trans_amount)
        val income: RadioButton = dialog.findViewById(R.id.type_income)
        val expense: RadioButton = dialog.findViewById(R.id.type_expense)
        val add_btn: Button = dialog.findViewById(R.id.trans_add)
        val cancelbtn: Button = dialog.findViewById(R.id.trans_cancel)



        cancelbtn.setOnClickListener {
            dialog.dismiss()
        }

        add_btn.setOnClickListener {
            val title = transtitle.text.toString()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = datepicker.minDate

            val dateString = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
            var wallet = ""
            var type = ""

            if (wal_cash.isChecked){
                wallet = "Cash"
            }else if(wal_bank.isChecked){
                wallet = "Bank"
            }else if(wal_card.isChecked){
                wallet = "Card"
            }

                val amountString: String = amount.text.toString()

            if (income.isChecked){
                type = "Income"
            }else if (expense.isChecked){
                type = "Expense"
            }
            if(title.isEmpty() || amountString.isEmpty() || wallet.isEmpty()
                || type.isEmpty()){
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            try {
                val amt = java.lang.Double.parseDouble(amountString)

                val dbHandler = DatabaseHandler(requireContext())

                var incomeAmount = 0.0
                var expenseAmount = 0.0

                if (type == "Income") {
                    incomeAmount = amt
                } else {
                    expenseAmount = amt
                }

                val success = dbHandler.insertDataTransactions(title, dateString, wallet, type, incomeAmount, expenseAmount)

                if (success) {
                    Toast.makeText(
                        requireContext(),
                        "Transaction added successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), "Error adding transaction", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Invalid amount format. Please enter a valid number.", Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss()


        }

        //setting the date picker listener
        val currentDate = Calendar.getInstance()
        datepicker.init(
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH),
            null
        )

       datepicker.setOnDateChangedListener { _, year, month, dayOfMonth ->
           val selectedDate = Calendar.getInstance()
           selectedDate.set(year, month, dayOfMonth)
           datepicker.minDate = selectedDate.timeInMillis
           val formatter = SimpleDateFormat("dd/MM/yyyy")
           date.setText(formatter.format(selectedDate.time))
           datepicker.visibility = View.GONE

       }



        date.setOnClickListener {
            datepicker.visibility = View.VISIBLE
           // datepicker.inputType = InputType.TYPE_NULL
        }

        dialog.show()

    }


        //display transactions in the recycler view
        @SuppressLint("NotifyDataSetChanged")
        private fun displayTransaction() {
            val newcursor: Cursor? = db.getTransactions()
            newArr.clear()
            while (newcursor!!.moveToNext()){
                val spenton = newcursor.getString(1)
                val date = newcursor.getString(3)
                val type = newcursor.getString(2)
                val amount = newcursor.getDouble(
                    when (type) {
                        "income" -> 5
                        else -> 6
                    }
                )
                newArr.add(TransactionDataList(spenton,date,type,amount))
            }
            newcursor.close()

            // Set the color of the amount text based on the type of transaction
//            for (i in 0 until newArr.size) {
//                if (newArr[i].type == "income") {
//                    newArr[i].amount.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.income_color)))
//                } else {
//                    newArr[i].amount.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.expense_color)))
//                }
//            }
            recyclerView.adapter?.notifyDataSetChanged()

        }



    }


