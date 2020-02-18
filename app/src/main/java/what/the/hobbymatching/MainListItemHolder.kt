package what.the.hobbymatching

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_main_list.view.*

class MainListItemHolder(view: View, parent: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val TAG = javaClass.simpleName
    private val parent = parent

    fun bind(match: Match) {
        var intersection = ""
        match.intersection.forEach {
            intersection += "$it "
        }
        itemView.textHobbyName.text = intersection
    }
}