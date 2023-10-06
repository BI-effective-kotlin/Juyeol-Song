package ps

import java.lang.Integer.min

/**
 * @author : Unagi_zoso
 * @date : 2023-09-26
 */

const val MAX_NUM_OF_NODES = 1000005

val br = System.`in`.bufferedReader()
val nodes = Array(MAX_NUM_OF_NODES) { ArrayList<Int>() }
val dp = Array(MAX_NUM_OF_NODES) { IntArray(2) { 0 } }
val visited = Array(MAX_NUM_OF_NODES) { false }

/**
 * Top-Down 방식
 * 0: 일반인, 1: 얼리어답터
 * 현재 노드가 일반인일 경우 자식들은 반드시 얼리어답터
 * 현재 노드가 얼리어답터일 경우 각 자식 중 일반인인 경우, 얼리어답터인 경우 비교해서 결정
 */
fun initializeDPTable(nodeId: Int) {
    visited[nodeId] = true
    dp[nodeId][1] = 1
    for (childId in nodes[nodeId]) {
        if (visited[childId]) continue
        initializeDPTable(childId)
        dp[nodeId][0] += dp[childId][1]
        dp[nodeId][1] += min(dp[childId][0], dp[childId][1])
    }
}

fun main() = with(System.out.bufferedWriter()) {
    repeat(br.readLine().toInt() - 1) {
        val (i, j) = br.readLine().split(' ').map { it.toInt() }
        nodes[i].add(j)
        nodes[j].add(i)
    }
    br.close()

    initializeDPTable(1)
    write("${min(dp[1][0], dp[1][1])}")
    close()
}
