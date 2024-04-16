
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pama.R
import com.example.pama.recyclerview.TransactionDataList

class TransactionAdapter(private var transactionlist: List<TransactionDataList>):
    RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.spent_on)
        val date: TextView = itemView.findViewById(R.id.date)
        val amount: TextView = itemView.findViewById(R.id.trans_amount)

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
        holder.title.text = currentTrans.title
        holder.date.text = currentTrans.date
        holder.amount.text = currentTrans.amount.toString()




        //display different colors for the income and expense
        val context = holder.itemView.context
        val colorRes = when(currentTrans.type){
            "income" -> R.color.income_color
            else -> R.color.expense_color
        }
        val color = context.getColor(colorRes)
        holder.amount.setBackgroundColor(color)

    }

//    fun updateData(newTransactions: List<TransactionDataList>) {
//        transactionlist = newTransactions
//        notifyDataSetChanged()
//    }

}