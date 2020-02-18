package what.the.hobbymatching


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SubListAdapter(context: Context) : RecyclerView.Adapter<SubListItemHolder>() {

    private val TAG = javaClass.simpleName
    private val context: Context = context
    var rawList = ArrayList<ArrayList<Hobby>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubListItemHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_sub_list, parent, false)
        return SubListItemHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return rawList.size
    }

    override fun onBindViewHolder(holder: SubListItemHolder, position: Int) {
        holder.bind(rawList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addRaw(list: ArrayList<ArrayList<Hobby>>) {
        Log.d(TAG, "* * * addRaw ::: ${list}")
        rawList.addAll(list)
        notifyDataSetChanged()
    }
}