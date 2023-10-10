package ps

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.PriorityQueue
import kotlin.math.max

/**
 * @author : Unagi_zoso
 * @date : 2023-10-07
 */

data class ComparablePair<T : Comparable<T>>(val timeCost: T, val nodeId: T) : Comparable<ComparablePair<T>> {
    override fun compareTo(other: ComparablePair<T>): Int {
        return timeCost.compareTo(other.timeCost)
    }
}

class DependencyList(
    private val _backingList: MutableList<ArrayList<ComparablePair<Int>>>,
) {
    val list: List<ArrayList<ComparablePair<Int>>>
        get() = _backingList
}

// timeCostList에 각 노드마다 자신의 노드까지 도달하기 위한 최저 시간 비용이 저장된다.
fun dijkstra(numOfComputers: Int, startNodeId: Int, dependencyList: DependencyList): IntArray {
    val pq = PriorityQueue<ComparablePair<Int>>()
    val timeCostList = IntArray(10005) { Int.MAX_VALUE }

    pq.add(ComparablePair(0, startNodeId))
    timeCostList[startNodeId] = 0
    while (pq.isNotEmpty()) {
        val nodeId = pq.poll().nodeId
        for (followedNode in dependencyList.list[nodeId]) { // followedNode 현재 노드의 종속을 가진 노드들. (더 좋은 이름이 있지 않을까 생각이 드네요.)
            val curTimeCost = timeCostList[nodeId] + followedNode.timeCost // 현재 노드까지의 최단 시간 비용 + 현재 노드에서 종속 가진 노드까지 시간 비용
            if (curTimeCost >= timeCostList[followedNode.nodeId]) continue // 현재 노드로부터 종속을 가진 노드로 이동하는게 더 효율적인 경우 처리한다.
            timeCostList[followedNode.nodeId] = curTimeCost
            pq.add(followedNode)
        }
    }

    return computeResult(numOfComputers, timeCostList)
}

fun computeResult(numOfComputers: Int, distList: IntArray): IntArray {
    val result = IntArray(2) { 0 }
    for (i in 1..numOfComputers) {
        if (distList[i] == Int.MAX_VALUE) continue
        result[0]++
        result[1] = max(distList[i], result[1])
    }
    return result
}

fun solve(reader: BufferedReader, writer: BufferedWriter) {
    val (n, d, c) = reader.readLine().split(' ').map { it.toInt() }

    val tempDependencyList = MutableList(100005) { ArrayList<ComparablePair<Int>>() }
    repeat(d) {
        val (a, b, s) = reader.readLine().split(' ').map { it.toInt() }
        tempDependencyList[b].add(ComparablePair(s, a))
    }
    val dependencyList = DependencyList(tempDependencyList)

    val result = dijkstra(n, c, dependencyList)

    writer.write("${result[0]} ${result[1]}\n")
}

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    br.use { reader ->
        bw.use { writer ->
            val t = reader.readLine().toInt()
            repeat(t) {
                solve(reader, writer)
            }
        }
    }
}
