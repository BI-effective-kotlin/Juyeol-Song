package ps.ps

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @author : Unagi_zoso
 * @date : 2023-11-07
 */
// https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=3&contestProbId=AYcllbDqUVgDFASR&categoryId=AYcllbDqUVgDFASR&categoryType=CODE&problemTitle=&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=3&pageSize=10&pageIndex=1

/**
 * 입력:
 * 전체 테스트 케이스 수 입력
 * 반지름 입력
 *
 * 중점이 원점인 원이다. 원이 포함하고 있는 점 중 정수 좌표로 이뤄진 점의 수를 출력하라.
 *
 * 해결로직:
 * 1 사분면 위의 정수 좌표를 가지는 점을 구한 뒤
 * 4배하여 전체 점을 구할 수 있다. 이후 원점의 수 (1)를 더한다.
 */
fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        for (turn in 1..reader.readLine().toInt()) {
            val radius = reader.readLine().toInt()
            var ans = 0
            for (col in 0..radius) {
                ans += sqrt(radius.toDouble().pow(2) - col.toDouble().pow(2)).toInt()
            }
            ans = 4 * ans + 1
            writer.write("#$turn $ans\n")
        }
    }
}
