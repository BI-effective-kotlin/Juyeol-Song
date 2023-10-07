package ps

import java.lang.Integer.max
import java.util.PriorityQueue

/**
 * @author : Unagi_zoso
 * @date : 2023-10-07
 */

val br = System.`in`.bufferedReader()
val bw = System.out.bufferedWriter()

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

fun solve() {
    val (n, d, c) = br.readLine().split(' ').map { it.toInt() }

    // backing property는 이렇게 쓰는건가요? 살짝 억지로 쓴 느낌이..
    val tempDependencyList = MutableList(100005) { ArrayList<ComparablePair<Int>>() }
    repeat(d) {
        val (a, b, s) = br.readLine().split(' ').map { it.toInt() }
        tempDependencyList[b].add(ComparablePair(s, a))
    }
    val dependencyList = DependencyList(tempDependencyList)

    val result = dijkstra(n, c, dependencyList)
    bw.write("${result[0]} ${result[1]}\n")
}

fun main() {
    val t = br.readLine().toInt()

    repeat(t) {
        solve()
    }

    br.close()
    bw.close()
}
