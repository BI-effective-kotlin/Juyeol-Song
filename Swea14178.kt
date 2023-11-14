package ps.ps

/**
 * @author Unagi_zoso
 * @since 2023-11-14
 */

// https://swexpertacademy.com/main/code/problem/problemDetail.do

fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        val times = reader.readLine().toInt()
        for (time in 1..times) {
            val (n, d) = reader.readLine().split(" ").map { it.toInt() }
            var i = 1
            var ans = 0
            while (i <= n) {
                ans += 1
                i += 2 * d + 1
            }
            writer.write("#$time $ans\n")
        }
    }
}
