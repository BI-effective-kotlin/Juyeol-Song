package ps.ps

/**
 * @author : Unagi_zoso
 * @date : 2023-10-20
 */
// https://www.acmicpc.net/problem/14003

fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        val n = reader.readLine().toInt()
        val inputList = mutableListOf(0) + reader.readLine().split(' ').map { it.toInt() }
        val indexList = Array(1000005) { 0 } // 각 inputList 원소들이 valueList의 몇 번 인덱스에 할당되었는지 저장된다.
        val valueList = MutableList(1) { 0 }

        makeLISListsWithInputList(indexList, valueList, inputList, n) // inputList를 통해 indexList, valueList를 만든다.

        val lis = findLIS(indexList, inputList, valueList.size - 1).joinToString(" ") { it.toString() }
        writer.write(
            """
            ${valueList.size - 1}
            $lis
            """.trimMargin(),
        )
    }
}

/**
 * @see kotlin.collections.binarySearch
 */
fun makeLISListsWithInputList(
    indexList: Array<Int>,
    valueList: MutableList<Int>,
    inputList: List<Int>,
    n: Int,
) {
    indexList[1] = 1
    valueList.add(inputList[1])
    for (inputIdx in 2..n) {
        if (valueList[valueList.size - 1] < inputList[inputIdx]) { // 현재 input값이 valueList의 값보다 클 경우 value에 새 원소로 추가
            valueList.add(inputList[inputIdx])
            indexList[inputIdx] = valueList.size - 1 // indexList에는 input값이 저장된 valueList의 인덱스가 저장된다
        } else { // valueList에서 현재 input값이 들어갈 수 있는 위치(인덱스)를 이분탐색으로 구한다.
            val result = valueList.binarySearch(element = inputList[inputIdx], 1, valueList.size).let {
                if (it < 0) -it - 1 else it // binarySearch에서 target을 찾지 못하면 negative 값을 반환한다.
            }

            valueList[result] = inputList[inputIdx]
            indexList[inputIdx] = result
        }
    }
}

fun findLIS(indexList: Array<Int>, inputList: List<Int>, _sizeOfLIS: Int): List<Int> {
    val resultList = MutableList(_sizeOfLIS + 1) { -1 }
    var sizeOfLIS = _sizeOfLIS
    for (i in inputList.size - 1 downTo 1) {
        if (sizeOfLIS == indexList[i]) {
            resultList[indexList[i]] = inputList[i]
            sizeOfLIS--
        }
    }
    return resultList.subList(1, resultList.size)
}
