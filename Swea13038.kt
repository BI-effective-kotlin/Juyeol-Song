package ps.ps

/**
 * @author Unagi_zoso
 * @since 2023-11-14
 */

// https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=3&problemLevel=4&contestProbId=AXxNn6GaPW4DFASZ&categoryId=AXxNn6GaPW4DFASZ&categoryType=CODE&problemTitle=&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=5
const val NUM_OF_DAYS = 7

fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        val times = reader.readLine().toInt()
        for (time in 1..times) {
            val n = reader.readLine().toInt()
            val arr = reader.readLine().split(" ").map { it.toInt() }
            var minValue = Int.MAX_VALUE
            for (index in arr.indices) {
                var curIndex = index
                var sumOfOne = 0
                var attendence = 0
                while (sumOfOne < n) {
                    sumOfOne += arr[curIndex % NUM_OF_DAYS]
                    curIndex++
                    attendence++
                }
                if (attendence < minValue) {
                    minValue = attendence
                }
            }
            writer.write("#$time $minValue\n")
        }
    }
}
