package ps.ps

/**
 * @author Unagi_zoso
 * @since 2023-11-14
 */

// https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=3&problemLevel=4&contestProbId=AV134DPqAA8CFAYh&categoryId=AV134DPqAA8CFAYh&categoryType=CODE&problemTitle=&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=5
// 현제 세대 양옆 2칸이 0일 경우 true 반환
fun isFineView(board: Array<IntArray>, r: Int, c: Int): Boolean {
    return board[r][c - 1] == 0 && board[r][c - 2] == 0 && board[r][c + 1] == 0 && board[r][c + 2] == 0
}

fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        for (time in 1..10) {
            val board = Array<IntArray> (1005) { IntArray(1005) }

            val col = reader.readLine().toInt()
            val rowList = reader.readLine().split(" ").map { it.toInt() }

            for (i in 0 until col) {
                for (j in 0 until rowList[i]) {
                    board[j][i] = 1
                }
            }

            var ans = 0
            for (i in 0 until col) {
                for (j in 0 until 256) {
                    if (board[j][i] == 1 && isFineView(board, j, i)) {
                        ans++
                    }
                }
            }

            writer.write("#$time $ans\n")
        }
    }
}
