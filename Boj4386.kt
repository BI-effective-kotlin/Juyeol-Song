package ps.ps

import java.util.PriorityQueue
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

/**
 * @author : Unagi_zoso
 * @date : 2023-10-10
 */
// https://www.acmicpc.net/problem/4386

data class CostAndPoints(
    val cost: Double,
    val lPoint: Int,
    val rPoint: Int,
) : Comparable<CostAndPoints> {

    override fun compareTo(other: CostAndPoints): Int {
        return this.cost.compareTo(other.cost)
    }
}

fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        val n = reader.readLine().toInt()
        val visited = Array(105) { BooleanArray(105) }
        val unionCheck = IntArray(105) { it } // index와 같은 값이 원소가 된다.
        val pq = PriorityQueue<CostAndPoints>()

        pq.initializeWithArray(Array(n) { reader.readLine().split(' ').map { it.toDouble() } })

        val ans = findMinCostOfSpanningTree(n, pq, visited, unionCheck)
        writer.write("${round(ans * 100) / 100}") // 소수점 셋 째자리에서 반올림
    }
}

fun unionFind(i: Int, unionCheck: IntArray): Int {
    if (unionCheck[i] == i) return i
    return unionFind(unionCheck[i], unionCheck)
}

fun PriorityQueue<CostAndPoints>.initializeWithArray(array: Array<List<Double>>) {
    array.forEachIndexed { lPointIdx, lPointPos ->
        for (rPointIdx in lPointIdx + 1 until array.size) {
            val (rPointX, rPointY) = array[rPointIdx]
            this.add(
                CostAndPoints(
                    sqrt((lPointPos[0] - rPointX).pow(2) + (lPointPos[1] - rPointY).pow(2)), // 점 사이의 거리
                    lPointIdx,
                    rPointIdx,
                ),
            )
        }
    }
}

fun findMinCostOfSpanningTree(
    n: Int,
    pq: PriorityQueue<CostAndPoints>,
    visited: Array<BooleanArray>,
    unionCheck: IntArray,
): Double {
    var ans = 0.0
    var numOfVertex = n - 1
    while (numOfVertex != 0) {
        val (cost, lPoint, rPoint) = pq.poll()
        if (visited[lPoint][rPoint] || visited[rPoint][lPoint]) continue
        if (unionFind(lPoint, unionCheck) == unionFind(rPoint, unionCheck)) continue

        visited[lPoint][rPoint] = true
        visited[rPoint][lPoint] = true
        unionCheck[unionFind(rPoint, unionCheck)] = unionCheck[lPoint]
        ans += cost
        numOfVertex--
    }
    return ans
}
