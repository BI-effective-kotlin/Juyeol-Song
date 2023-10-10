package ps

/**
 * @author : Unagi_zoso
 * @date : 2023-09-26
 */

val check = Array(1005) { false }

// 이펙티브 코틀린 아이템 1. 16페이지 참고. 변경 가능 지점이 프로퍼티 자체. (한 번 써보고 싶어서 넣어봤습니다!)
// 멀티쓰레드 관점에서 더 안정적일 순 있어도 기존 자바의 String처럼 GC 성능에 영향을 주진 않을까? 하는 생각이 드네요.
var rectangles = arrayOf<Rectangle>()

// 어떻게 하면 split()을 부생성자에게 넣을 수 있을지 고민하다. backing property 로 만들어보았습니다.
// 부생성자를 통해 딱 한 번 _x1, _y1, _x2, _y2가 할당됩니다.
// component 정의 위해서 data class에서 class로 변경하였습니다.
class Rectangle(
    private var _x1: Int = 0,
    private var _y1: Int = 0,
    private var _x2: Int = 0,
    private var _y2: Int = 0,
) {
    val x1: Int
        get() = _x1

    val y1: Int
        get() = _y1

    val x2: Int
        get() = _x2

    val y2: Int
        get() = _y2

    constructor(inputStr: String) : this() {
        inputStr.split(' ').map { it.toInt() }.let { _x1 = it[0]; _y1 = it[1]; _x2 = it[2]; _y2 = it[3] }
    }

    operator fun component1(): Int {
        return _x1
    }

    operator fun component2(): Int {
        return _y1
    }

    operator fun component3(): Int {
        return _x2
    }

    operator fun component4(): Int {
        return _y2
    }
}

fun main() {
    var ans = 0
    System.`in`.bufferedReader().use { reader ->
        val n = reader.readLine().toInt()
        repeat(n) { rectangles += Rectangle(reader.readLine()) } // readLine() 에서 'Possibly blocking call in non-blocking context could lead to thread starvation' warning이 뜨는데 어떤 왜 발생하는지 잘 모르겠습니다..
        // rectangles = Array(n) { Rectangle(reader.readLine()) }

        for (i in 0 until n) {
            val (x1, y1, x2, y2) = rectangles[i]

            if (isContainStartPos(x1, y1, x2, y2)) ans--

            if (check[i]) continue
            check[i] = true
            unionFind(i, n)
            ans++
        }
    }

    System.out.bufferedWriter().use { it.write("$ans") }
}

fun isContainStartPos(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    return ((x1 == 0 || x2 == 0) and (y1 <= 0 && y2 >= 0)) or
        ((y1 == 0 || y2 == 0) and (x1 <= 0 && x2 >= 0))
}

fun unionFind(idx: Int, numOfRectangles: Int) {
    for (i in 0 until numOfRectangles) {
        if (check[i] or isNotOverlap(rectangles[idx], rectangles[i])) continue
        check[i] = true
        unionFind(i, numOfRectangles)
    }
}

/*
 * 두 사각형의 가장 왼쪽, 오른쪽, 위, 아래 위치를 비교해 겹쳐지는 부분이 없는지 반환한다.
 */
fun isNotOverlap(lhs: Rectangle, rhs: Rectangle): Boolean {
    val lhsLeft: Int; val lhsRight: Int; val lhsAbove: Int; val lhsBottom: Int
    val rhsLeft: Int; val rhsRight: Int; val rhsAbove: Int; val rhsBottom: Int

    if (lhs.x1 < lhs.x2) { lhsLeft = lhs.x1; lhsRight = lhs.x2 } else { lhsLeft = lhs.x2; lhsRight = lhs.x1 }
    if (lhs.y1 < lhs.y2) { lhsBottom = lhs.y1; lhsAbove = lhs.y2 } else { lhsBottom = lhs.y2; lhsAbove = lhs.y1 }
    if (rhs.x1 < rhs.x2) { rhsLeft = rhs.x1; rhsRight = rhs.x2 } else { rhsLeft = rhs.x2; rhsRight = rhs.x1 }
    if (rhs.y1 < rhs.y2) { rhsBottom = rhs.y1; rhsAbove = rhs.y2 } else { rhsBottom = rhs.y2; rhsAbove = rhs.y1 }

    return (
        (lhsLeft > rhsRight) or (lhsRight < rhsLeft) or (lhsAbove < rhsBottom) or (lhsBottom > rhsAbove) or
            ((lhsLeft < rhsLeft) and (lhsAbove > rhsAbove) and (lhsRight > rhsRight) and (lhsBottom < rhsBottom)) or
            ((rhsLeft < lhsLeft) and (rhsAbove > lhsAbove) and (rhsRight > lhsRight) and (rhsBottom < lhsBottom))
        )
}
