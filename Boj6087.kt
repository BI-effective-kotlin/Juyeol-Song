package ps

import java.util.LinkedList

/**
 * @author : Unagi_zoso
 * @date : 2023-10-05
 */

val br = System.`in`.bufferedReader()
val board = mutableListOf<String>()
val visited = Array(105) { IntArray(105) { -1 } }
val startAndDest = arrayListOf<Pair<Int, Int>>()

val dirX = arrayOf(-1, 1, 0, 0)
val dirY = arrayOf(0, 0, -1, 1)

data class Node(val y: Int, val x: Int)

fun main() = with(System.out.bufferedWriter()) {
    br.use { reader ->
        val (m, n) = reader.readLine().split(' ').map { it.toInt() }
        for (row in 0 until n) {
            board.add(
                br.readLine().apply {
                    this.forEachIndexed { col, ch ->
                        if (ch == 'C') {
                            startAndDest.add(Pair(row, col))
                        }
                    }
                },
            )
        }

        use { write("${ bfs(n, m) }") }
    }
}

fun bfs(n: Int, m: Int): Int {
    var curQ = LinkedList<Node>()
    var nextQ = LinkedList<Node>()

    val startY = startAndDest[0].first
    val startX = startAndDest[0].second

    curQ.add(Node(startY, startX))

    /**
     * 직진(이전 방향과 동일)인 경우 curQ에 담아 최대한 나아간다.
     * 회전(이전 방향과 다름)인 경우 nexQ에 담아 curQ가 소진될 때 까지 대기한다.
     * 회전한 횟수가 낮은 경우를 우선적으로 접근해 최소의 해를 구한다. (curQ와 nexQ의 회전횟수는 1 차이다.)
     */

    var cnt = 0
    val maxDistance = 105
    while (true) {
        while (curQ.isNotEmpty()) {
            val (y, x) = curQ.poll()
            for (i in 0..3) {
                var nextY = y
                var nextX = x
                for (j in 0 until maxDistance) { // 직진을 위한 반복
                    nextY += dirY[i]
                    nextX += dirX[i]
                    if (nextY !in 0 until n || nextX !in 0 until m || board[nextY][nextX] == '*') break
                    if (cnt == visited[nextY][nextX]) continue // 가로막힌 경우 같은 회전횟수라면 점프한다. (nextQ에 노드 추가 없이 다음 좌표로 건너간다.)
                    if (visited[nextY][nextX] != -1) break
                    if (nextY == startAndDest[1].first && nextX == startAndDest[1].second) return cnt

                    visited[nextY][nextX] = cnt
                    nextQ.add(Node(nextY, nextX))
                }
            }
        }
        curQ = nextQ
        nextQ = LinkedList()
        cnt++
    }
}
