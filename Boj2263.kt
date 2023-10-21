package ps

import java.io.BufferedWriter

/**
 * @author : Unagi_zoso
 * @date : 2023-10-11
 */
// https://www.acmicpc.net/problem/2263

/**
 *                  1
 *                /   \
 *              2      3
 *            /  \   /  \
 *          4    5  6     7
 *        /  \
 *       8    9
 *       inorder: 8 4 9 2 5 1 6 3 7
 *       postorder: 8 9 4 5 2 6 7 3 1
 *
 *       postorder의 마지막 원소의 값을 inorder의 루트로써 왼쪽, 오른쪽 둘로 나누면 기존 트리를 추적할 수 있다.
 *       ex ) postorder의 last : 1
 *       이 때 inorder는 8 4 9 2 5       1 (root)       6 3 7    이렇게 나눌 수 있다.
 *       1이 전체 트리의 루트가 되며 각 서브트리들의 루트들도 이와 같이 찾을 수 있다.
 */

lateinit var inOrderList: List<Int>
lateinit var postOrderList: List<Int>
val idxOfInOrderLookUp = IntArray(100_005) // postOrderList에서 찾은 값이 inOrderList의 몇 번 index에 있는지 저장하여 빠르게 접근할 수 있게 한다.

// 현재 트리의 루트를 출력한다. 출력형식 ex) "rootValue "
// 트리의 범위만큼 inOrderList index와 postOrderIndex를 슬라이싱하여 자식 노드를 루트로 한 서브 트리로 재귀호출한다.
fun printPreOrder(inStart: Int, inLast: Int, postStart: Int, postLast: Int, writer: BufferedWriter) {
    if (inStart > inLast || postStart > postLast) {
        return
    }
    val idxOfParent = idxOfInOrderLookUp[postOrderList[postLast]]
    writer.write("${inOrderList[idxOfParent]} ")

    val sizeOfLeft = idxOfParent - inStart
    val sizeOfRight = inLast - idxOfParent

    // left child 재귀호출
    printPreOrder(
        inStart,
        inStart + sizeOfLeft - 1,
        postStart,
        postStart + sizeOfLeft - 1,
        writer,
    )
    // right child 재귀호출
    printPreOrder(
        inLast - sizeOfRight + 1,
        inLast,
        postLast - sizeOfRight,
        postLast - 1,
        writer,
    )
}

fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        val n = reader.readLine().toInt()
        inOrderList = reader.readLine().split(' ').map { it.toInt() }
        inOrderList.forEachIndexed { index, i ->
            idxOfInOrderLookUp[i] = index
        }
        postOrderList = reader.readLine().split(' ').map { it.toInt() }.toMutableList()

        printPreOrder(0, inOrderList.lastIndex, 0, postOrderList.lastIndex, writer)
    }
}
