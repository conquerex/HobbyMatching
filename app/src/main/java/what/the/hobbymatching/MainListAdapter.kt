package what.the.hobbymatching


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainListAdapter(context: Context) : RecyclerView.Adapter<MainListItemHolder>() {

    private val TAG = javaClass.simpleName
    private val context: Context = context
    var list = ArrayList<Match>()
    var rawData = ArrayList<MutableSet<String>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListItemHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false)
        return MainListItemHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainListItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(context, SubActivity::class.java)
            var hobbyList = ArrayList<Hobby>()

            for (i in list[position].positionList) {
                var hobbies = ""
                // 포지션 번호는 1번부터 시작하기 때문에
                // 1을 빼주는 index를 적용해야 한다.
                rawData[i - 1].forEach {
                    hobbies += "$it "
                }
                val hobby = Hobby(i, hobbies)
                hobbyList.add(hobby)
            }

            var bundle = Bundle()
            bundle.putSerializable("hobby", hobbyList as Serializable)
            bundle.putSerializable("match", list[position] as Serializable)
            intent.putExtra("match", bundle)
            context.startActivity(intent)
        }
        holder.bind(list[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun clearList() {
        list.clear()
        this.rawData.clear()
        notifyDataSetChanged()
    }

    fun addHobby(matchList: ArrayList<Match>, rawData: ArrayList<MutableSet<String>>) {
        for (i in 0 until matchList.size) {
            list.add(matchList[i])
        }
        this.rawData = rawData

        notifyDataSetChanged()
    }
}