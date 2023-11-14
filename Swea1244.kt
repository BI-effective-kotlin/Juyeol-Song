package ps.ps

/**
 * @author Unagi_zoso
 * @since 2023-11-14
 */

// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV15Khn6AN0CFAYD
var ans = 0
var maxDepth = 0
lateinit var isVisited: HashSet<Pair<Int, Int>>

fun rec(array: IntArray, depth: Int) {
    if (depth == maxDepth) {
        val number = array.joinToString("").toInt()
        if (number > ans) ans = number
        return
    }
    for (i in array.indices) {
        for (j in i + 1 until array.size) {
            array[i] = array[j].also { array[j] = array[i] }
            val number = array.joinToString("").toInt()

            if (isVisited.contains(Pair(number, depth))) {
                array[i] = array[j].also { array[j] = array[i] }
                continue
            }
            isVisited.add(Pair(number, depth))
            rec(array, depth + 1)
        }
    }
}
fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        val times = reader.readLine().toInt()
        for (time in 1..times) {
            val (arr, n) = reader.readLine().split(" ")
            val numbers = arr.map { it.toString().toInt() }.toIntArray()
            maxDepth = n.toInt()
            ans = 0
            isVisited = HashSet()
            rec(numbers, 0)

            writer.write("#$time $ans\n")
        }
    }
}
