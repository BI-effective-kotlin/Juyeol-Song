package ps

/**
 * @author : Unagi_zoso
 * @date : 2023-10-14
 */
// https://www.acmicpc.net/problem/2493

data class TowerInfo(val id: Int, val height: Int)

val resultList = IntArray(500_005)

object Stack {
    private val stk = mutableListOf<TowerInfo>()

    fun checkAndAdd(id: Int, height: Int) {
        checkAndAdd(TowerInfo(id + 1, height))
    }

    private fun checkAndAdd(curTower: TowerInfo) {
        if (stk.isEmpty()) {
            stk.add(curTower)
            return
        }

        if (stk.last().height > curTower.height) {
            resultList[curTower.id] = stk.last().id
        } else { // 바로 앞의 타워가 현재의 타워보다 작을 경우
            // 큰 타워가 나올 때까지 stk에서 pop 한다.
            while (stk.isNotEmpty() && stk.last().height < curTower.height) {
                stk.removeLast()
            }
            resultList[curTower.id] = stk.lastOrNull()?.id ?: 0
        }
        stk.add(curTower)
    }
}

fun main() = System.`in`.bufferedReader().use { reader ->
    val n = reader.readLine().toInt()
    val inputList = reader.readLine().split(' ').map { it.toInt() }

    inputList.forEachIndexed(Stack::checkAndAdd)

    System.out.bufferedWriter().use { writer ->
        for (i in 1..n) {
            writer.write("${resultList[i]} ")
        }
    }
}
