
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.pama.R
import com.example.pama.recyclerview.TransactionDataList

class TransactionAdapter(private val transactionlist: ArrayList<TransactionDataList>):
    RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: EditText = itemView.findViewById(R.id.spent_on)
        val date: EditText = itemView.findViewById(R.id.date)
        val amount: EditText = itemView.findViewById(R.id.trans_amount)




        fun bind(transaction: TransactionDataList) {


            title.setText(transaction.title)
            date.setText(transaction.date)
            amount.setText(transaction.amount.toString())

            if (transaction.type == "income") {
                amount.setBackgroundResource(R.color.income_color)
            } else {
                amount.setBackgroundResource(R.color.expense_color)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val transView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item,parent,false)
        return MyViewHolder(transView)
    }

    override fun getItemCount(): Int {
        return transactionlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTrans = transactionlist[position]
        holder.bind(currentTrans)

    }

}