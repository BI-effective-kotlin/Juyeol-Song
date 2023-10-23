package ps.ps

/**
 * @author : Unagi_zoso
 * @date : 2023-10-19
 */
// https://www.acmicpc.net/problem/14002

fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        val n = reader.readLine().toInt()
        val dpTable = Array<MutableList<String>>(1005) { mutableListOf() }
        val numbers = reader.readLine().split(' ').map { it.toInt() }

        repeat(n) { i ->
            repeat(i) { j ->
                if (numbers[j] < numbers[i] && dpTable[j].size + 1 > dpTable[i].size) {
                    dpTable[i] = dpTable[j].toMutableList()
                }
            }
            dpTable[i].add(numbers[i].toString())
        }

        val answer = dpTable.maxBy { it.size }
        writer.write(
            """${answer.size}
               |${answer.joinToString(" ")}
            """.trimMargin(),
        )
    }
}
