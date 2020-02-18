package what.the.hobbymatching

import java.io.Serializable

data class Match(
    val intersection: MutableSet<String>,
    val positionList: HashSet<Int>
): Serializable