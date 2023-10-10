package ps

import java.util.LinkedList

/**
 * @author : Unagi_zoso
 * @date : 2023-10-06
 */

// a, b, c 3개 중 2개를 순서에 관계 없이 뽑는다.
fun getCombinations(a: Int, b: Int, c: Int) = listOf(a to b, a to c, b to c)

fun bfs(x: Int, y: Int, z: Int): Int {
    val visited = Array(1505) { BooleanArray(1505) { false } }
    val q = LinkedList<Pair<Int, Int>>()
    val sumOfAllStones = x + y + z

    q.push(Pair(x, y))
    visited[x][y] = true
    while (q.isNotEmpty()) {
        val (l, r) = q.poll()
        val other = sumOfAllStones - l - r

        if ((l == r) and (r == other)) return 1

        for ((i, j) in getCombinations(l, r, other)) {
            val (nextX, nextY) = when {
                // 세 집합 중 최댓값, 최솟값을 각 각 Pair의 첫, 두 번째 원소에 둬 visited 접근범위를 제한한다.
                i > j -> Pair(maxOf(i - j, j + j, sumOfAllStones - i - j), minOf(i - j, j + j, sumOfAllStones - i - j))
                i < j -> Pair(maxOf(i + i, j - i, sumOfAllStones - i - j), minOf(i + i, j - i, sumOfAllStones - i - j))
                else -> continue
            }
            if (visited[nextX][nextY]) continue
            visited[nextX][nextY] = true
            q.push(Pair(nextX, nextY))
        }
    }
    return 0
}

fun main() = with(System.out.bufferedWriter()) {
    val br = System.`in`.bufferedReader()
    val (x, y, z) = br.use { reader ->
        reader.readLine().split(' ').map { it.toInt() }
    }

    use { writer ->
        if ((x + y + z) % 3 == 0) writer.write("${bfs(x, y, z)}") else writer.write("0")
    }
}
