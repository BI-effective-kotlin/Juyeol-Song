package ps

import java.util.LinkedList

/**
 * @author : Unagi_zoso
 * @date : 2023-10-06
 */

data class Pair(val x: Int, val y: Int)

// a, b, c 3개 중 2개를 순서에 관계 없이 뽑는다.
fun getCombinations(a: Int, b: Int, c: Int) = listOf(a to b, a to c, b to c)

fun bfs(x: Int, y: Int, z: Int): Int {
    val visited = Array(1505) { BooleanArray(1505) { false } }
    val q = LinkedList<Pair>()
    val sumOfAllStones = x + y + z

    q.push(Pair(x, y))
    visited[x][y] = true
    while (q.isNotEmpty()) { // 변수 재사용하기 싫어 의미없이 변수를 만드는 데 괜찮은건가 생각이 드네요.
        val (l, r) = q.poll()
        val other = sumOfAllStones - l - r

        if ((l == r) and (r == other)) return 1

        for ((i, j) in getCombinations(l, r, other)) {
            val newPair = when {
                // 세 집합 중 최댓값, 최솟값을 각 각 Pair의 첫, 두 번째 원소에 둬 visited 접근범위를 제한한다.
                i > j -> Pair(maxOf(i - j, j + j, sumOfAllStones - i - j), minOf(i - j, j + j, sumOfAllStones - i - j))
                i < j -> Pair(maxOf(i + i, j - i, sumOfAllStones - i - j), minOf(i + i, j - i, sumOfAllStones - i - j))
                else -> continue
            }
            if (visited[newPair.x][newPair.y]) continue
            visited[newPair.x][newPair.y] = true
            q.push(newPair)
        }
    }
    return 0
}

fun main() = with(System.out.bufferedWriter()) {
    val br = System.`in`.bufferedReader()
    val (x, y, z) = br.readLine().split(' ').map { it.toInt() }
    br.close()

    if ((x + y + z) % 3 == 0) print(bfs(x, y, z)) else print(0)
    close()
}
