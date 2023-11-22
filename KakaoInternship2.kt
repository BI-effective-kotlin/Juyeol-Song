import java.util.LinkedList

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
// 2022 카카오 인턴십 2번 문제  두 큐 합 같게 만들기
// https://school.programmers.co.kr/learn/courses/30/lessons/118667
class Solution {
    fun solution(queue1: IntArray, queue2: IntArray): Int {
        var sumOfQ1 = queue1.sum().toLong()
        var sumOfQ2 = queue2.sum().toLong()
        val q1 = LinkedList<Int>().apply { addAll(queue1.toList()) }
        val q2 = LinkedList<Int>().apply { addAll(queue2.toList()) }
        var round = 0
        var answer = -1
        for (i in 0..4 * queue1.size) {
            if (sumOfQ1 < sumOfQ2) {
                val q2Value = q2.poll()
                q1.addLast(q2Value)
                sumOfQ1 += q2Value
                sumOfQ2 -= q2Value
                round++
            } else if (sumOfQ1 > sumOfQ2) {
                val q1Value = q1.poll()
                q2.addLast(q1Value)
                sumOfQ1 -= q1Value
                sumOfQ2 += q1Value
                round++
            } else {
                answer = round
                break
            }
        }
        return answer
    }
}
