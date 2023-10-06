package ps

/**
 * @author : Unagi_zoso
 * @date : 2023-09-26
 */

var n = 0
var ans = 0
val check = Array(1005) { false }
lateinit var rectangles: Array<Rectangle>

data class Rectangle(
    val x1: Int = 0,
    val y1: Int = 0,
    val x2: Int = 0,
    val y2: Int = 0,
) {
    constructor(list: List<String>) : this(list[0].toInt(), list[1].toInt(), list[2].toInt(), list[3].toInt())
}

fun main() = with(System.out.bufferedWriter()) {
    val br = System.`in`.bufferedReader()
    n = br.readLine().toInt()
    rectangles = Array(n) { br.readLine().split(" ").let { Rectangle(it) } }
    br.close()

    for (i in 0 until n) {
        val (x1, y1, x2, y2) = rectangles[i]

        if (isContainStartPos(x1, y1, x2, y2)) ans--

        if (check[i]) continue
        check[i] = true
        unionFind(i)
        ans++
    }

    write("$ans")
    close()
}

fun isContainStartPos(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    return (x1 == 0 && (y1 <= 0 && y2 >= 0)) || (x2 == 0 && (y1 <= 0 && y2 >= 0)) ||
        (y1 == 0 && (x1 <= 0 && x2 >= 0)) || (y2 == 0 && (x1 <= 0 && x2 >= 0))
}

fun unionFind(idx: Int) {
    for (i in 0 until n) {
        if (check[i] or isNotOverlap(rectangles[idx], rectangles[i])) continue
        check[i] = true
        unionFind(i)
    }
}

/*
 * 두 사각형의 가장 왼쪽, 오른쪽, 위, 아래 위치를 비교해 겹쳐지는 부분이 없는지 반환한다.
 */
// 함수를 호출하는 부분에서(50번 줄) ! 연산자를 쓰고싶지 않아 isOverlap() -> isNotOverlap()으로 수정하였는데 클린코드 관점에서 괜찮은 방식일까요? 큰 영향이 있나 싶은 생각도 드네요.
fun isNotOverlap(lhs: Rectangle, rhs: Rectangle): Boolean {
    val lhsLeft: Int; val lhsRight: Int; val lhsAbove: Int; val lhsBottom: Int
    val rhsLeft: Int; val rhsRight: Int; val rhsAbove: Int; val rhsBottom: Int

    // 중복되어 보이는 코드 같은데 이거다 싶은 개선법이 잘 안 떠오르네요.
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

// 디버깅용
fun showAllRectangles(list: List<Rectangle>) {
    list.forEach { print("${it.x1}  ${it.y1}  ${it.x2}  ${it.y2} \n") }
}
