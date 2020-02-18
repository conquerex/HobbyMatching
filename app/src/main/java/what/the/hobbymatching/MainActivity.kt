package what.the.hobbymatching

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * 현재 로직으로는 1만개와 50만개의 경우를 커버하지 못한다.
 * 다른 대안이 필요한 상황
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = javaClass.simpleName

    var listAdapter = MainListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "커플 매칭"

        button100.setOnClickListener(this)
        button1000.setOnClickListener(this)
        button10000.setOnClickListener(this)
        button500000.setOnClickListener(this)

        listAdapter.setHasStableIds(true)
        listMainHobby.layoutManager = LinearLayoutManager(this)
        listMainHobby.adapter = listAdapter

        readTxt(R.raw.hobby100)
    }

    fun inputTxt(input: Int) {
        statusLoding(true)
        Handler().postDelayed({
            readTxt(input)
        }, 500)
    }

    fun readTxt(input: Int) {
        // 초기화
//        statusLoding(true)
        listAdapter.clearList()

        var interSet: ArrayList<Match> = ArrayList()
        val rawData = ArrayList<MutableSet<String>>()

        val inputStream: InputStream = this.resources.openRawResource(input)
        val br = BufferedReader(InputStreamReader(inputStream))
        var lineCount = 0
        var line: String = ""

        // 기준이 될 raw list
        val standardSet: HashSet<String> = HashSet()
        // intersection 사이즈 (교집합이 제일 큰 녀석)
        var maxMatch: Int = 0
        var i = 0

        while (br.readLine().also { line = it } != null) {
            if (i == 0) {
                lineCount = line.toIntOrNull()!!
            } else {
                rawData.add(HashSet(line.split(" ")))
            }

            if (rawData.size < lineCount) {
                i++
            } else {
                break
            }
        }

        for (position in 0 until rawData.size) {
            if (position == 0) {
                standardSet.addAll(rawData[position])
            } else {
                var tempIntersection: MutableSet<String> = HashSet(standardSet)
                // standardSet과 다른 raw들을 비교
                for (j in position until rawData.size) {
                    val tempCurSet: HashSet<String> = HashSet(rawData[j])
                    val temp = HashSet(standardSet)
                    temp.retainAll(tempCurSet)
                    var size = temp.size
                    if (size > maxMatch) {
                        interSet = ArrayList()
                        maxMatch = size
                        tempIntersection = temp
                        var positionList = HashSet<Int>()
                        positionList.add(position)
                        positionList.add(j + 1)
                        interSet.add(Match(tempIntersection, positionList))
                    } else if (size == maxMatch) {
                        var isNewInter = false
                        for (k in 0 until interSet.size) {
                            if (temp.equals(interSet[k].intersection)) {
                                // interSet에 동일한 교집합이 있는 경우
                                interSet[k].positionList.add(j + 1)
                                break
                            } else {
                                if (k == interSet.size - 1) {
                                    isNewInter = true
                                }
                            }
                        }
                        if (isNewInter) {
                            var positionList = HashSet<Int>()
                            positionList.add(position)
                            positionList.add(j + 1)
                            interSet.add(Match(temp, positionList))
                        }
                    }
                }
            }

            standardSet.clear()
            standardSet.addAll(rawData[position])
        }

        inputStream.close()
        Log.d(TAG, "* * * interSet ::: ${interSet}")
        setAdapter(interSet, rawData)
    }

    private fun statusLoding(status: Boolean) {
        button100.isClickable = !status
        button1000.isClickable = !status
        button10000.isClickable = !status
        button500000.isClickable = !status

        if (status) {
            textLoading.visibility = View.VISIBLE
        } else {
            textLoading.visibility = View.GONE
        }
    }

    private fun setAdapter(list: ArrayList<Match>, rawData: ArrayList<MutableSet<String>>) {
        listAdapter.addHobby(list, rawData)
        statusLoding(false)

        listMainHobby.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 스크롤을 대비함
            }
        })
    }

    override fun onClick(p0: View?) {
        when (p0) {
            button100 -> inputTxt(R.raw.hobby100)
            button1000 -> inputTxt(R.raw.hobby1000)
            button10000 -> readTxt(R.raw.hobby10000)
            button500000 -> readTxt(R.raw.hobby500000)
            else -> readTxt(R.raw.hobby100)
        }
    }
}
