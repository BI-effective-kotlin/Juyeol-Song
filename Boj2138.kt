package ps

import kotlin.math.min

/**
 * @author : Unagi_zoso
 * @date : 2023-10-14
 */
// https://www.acmicpc.net/problem/2138

/**
 * 현재 인덱스 idx는 idx - 1 번의 값에 최종적 결정을 할 수 있다.
 * 첫 인덱스만 switch를 켜는 경우와 켜지 않는 경우로 결정한 다음, 두 번째 인덱스부터 앞 인덱스의 값을 target list의 값에 일치하게 결정해주면
 * 두 경우 중 target list와 일치하는 경우 switchCount가 가장 작은 값을 출력한다.
 */
fun main() = System.`in`.bufferedReader().use() { reader ->
    System.out.bufferedWriter().use() { writer ->
        val n = reader.readLine().toInt()
        val start = reader.readLine().map { it.code - '0'.code }
        val target = reader.readLine().map { it.code - '0'.code }

        val resultFirstOn = init(
            start = start.toMutableList().apply { this.switchOn(0) },
            target = target,
            _switchCount = 1,
        )
        val resultFirstOff = init(
            start = start.toMutableList(),
            target = target,
            _switchCount = 0,
        )

        if (resultFirstOn == Int.MAX_VALUE && resultFirstOff == Int.MAX_VALUE) {
            writer.write("-1")
        } else {
            writer.write("${min(resultFirstOn, resultFirstOff)}")
        }
    }
}

// start의 i - 1 인덱스의 값이  target의 것과 일치되게 switchOn를 한다.
// 최종적으로 start와 target이 일치되었다면 switch한 회수(switchCount)를 반환한다.
// 일치하지 않을 시 Int.MAX_VALUE를 반환한다.
fun init(start: MutableList<Int>, target: List<Int>, _switchCount: Int): Int {
    var switchCount = _switchCount

    for (i in 1 until start.size) {
        if (start[i - 1] != target[i - 1]) {
            start.switchOn(idx = i)
            switchCount++
        }
    }

    if (start.isNotEqualOtherList(target)) {
        return Int.MAX_VALUE
    }
    return switchCount
}

// idx - 1, idx, idx + 1 값의 비트를 반전시킨다.
fun MutableList<Int>.switchOn(idx: Int) {
    for (dir in -1..1) {
        val nextIdx = idx + dir
        if (nextIdx !in 0 until this.size) { continue }
        this[nextIdx] = (this[nextIdx] + 1) % 2
    }
}

// 현재 list가 다른 list와 일치하지 않는지 boolean으로 반환한다.
// 일치하지 않을 시 true, 일치할 시 false
fun List<Int>.isNotEqualOtherList(other: List<Int>): Boolean {
    this.forEachIndexed { index, value ->
        if (value != other[index]) {
            return true
        }
    }
    return false
}
