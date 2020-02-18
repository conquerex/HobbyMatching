package what.the.hobbymatching

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_sub.*

class SubActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    var listAdapter = SubListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        listAdapter.setHasStableIds(true)
        listSubHobby.layoutManager = LinearLayoutManager(this)
        listSubHobby.adapter = listAdapter

        val args: Bundle = intent.getBundleExtra("match")

        var match = args.getSerializable("match") as Match
        var intersection = "공통된 취미 : "
        match.intersection.forEach {
            intersection += "$it "
        }
        title = intersection

        var rawList = args.getSerializable("hobby") as ArrayList<Hobby>
        var list = ArrayList<ArrayList<Hobby>>()
        for (i in 0 until rawList.size) {
            for (j in i + 1 until rawList.size) {
                var pairHobby = ArrayList<Hobby>()
                pairHobby.add(rawList[i])
                pairHobby.add(rawList[j])
                list.add(pairHobby)
            }
        }

        setAdapter(list)
    }

    private fun setAdapter(list: ArrayList<ArrayList<Hobby>>) {
        listAdapter.addRaw(list)

        listSubHobby.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 스크롤을 대비함
            }
        })
    }
}
