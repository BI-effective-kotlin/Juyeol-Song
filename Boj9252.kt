package ps

import java.util.LinkedList
import kotlin.math.max

/**
 * @author : Unagi_zoso
 * @date : 2023-10-21
 */
// https://www.acmicpc.net/problem/9252

fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        val leftString = reader.readLine()
        val rightString = reader.readLine()

        // [0][n], [n][0] 는 0으로 패딩 적용 (0 <= n <= max(rightString.length, leftString.length)
        val board = Array(rightString.length + 1) { IntArray(leftString.length + 1) }

        makeLCSBoard(rightString, leftString, board)

        val resultLCS = findLCS(board, leftString, rightString)
        writer.write(
            """
            ${resultLCS.length}
            $resultLCS
            """.trimIndent(),
        )
    }
}

fun makeLCSBoard(rightString: String, leftString: String, board: Array<IntArray>) {
    for (i in rightString.indices) {
        for (j in leftString.indices) {
            board[i + 1][j + 1] = if (leftString[j] == rightString[i]) {
                board[i][j] + 1
            } else {
                max(board[i][j + 1], board[i + 1][j])
            }
        }
    }
}

fun findLCS(board: Array<IntArray>, leftString: String, rightString: String): String {
    val lcsString = StringBuilder()

    val q = LinkedList<Pair<Int, Int>>()
    q.add(rightString.length to leftString.length)
    while (q.isNotEmpty()) {
        val (curY, curX) = q.poll()
        if (board[curY][curX] == 0) break
        if (board[curY][curX] == board[curY][curX - 1]) {
            q.add(curY to curX - 1)
        } else if (board[curY][curX] == board[curY - 1][curX]) {
            q.add(curY - 1 to curX)
        } else {
            lcsString.append(leftString[curX - 1])
            q.add(curY - 1 to curX - 1)
        }
    }
    return lcsString.toString().reversed()
}
