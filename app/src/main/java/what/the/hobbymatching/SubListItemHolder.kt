package what.the.hobbymatching

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_sub_list.view.*

class SubListItemHolder(view: View, parent: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val TAG = javaClass.simpleName
    private val parent = parent

    fun bind(pairHobby: ArrayList<Hobby>) {
        var first = pairHobby[0]
        var second = pairHobby[1]

        if (pairHobby[0].position > pairHobby[1].position) {
            first = pairHobby[1]
            second = pairHobby[0]
        }

        itemView.textPositionPair.text = "${first.position}-${second.position}"
        itemView.textHobbyFirst.text = first.hobbies
        itemView.textHobbySecond.text = second.hobbies
    }
}