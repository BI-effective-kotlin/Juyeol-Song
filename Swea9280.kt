package ps.ps

import java.util.PriorityQueue

/**
 * @author Unagi_zoso
 * @since 2023-11-15
 */
// https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=3&problemLevel=4&contestProbId=AW9j74FacD0DFAUY&categoryId=AW9j74FacD0DFAUY&categoryType=CODE&problemTitle=&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=11

fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        val times = reader.readLine().toInt()
        for (time in 1..times) {
            val carParkingNumbers = IntArray(2002) // 인덱스로 자동차를 식별해 어느 주차공간을 사용 중인지 저장
            val (n, m) = reader.readLine().split(" ").map { it.toInt() }
            val parkingCosts = IntArray(1) + IntArray(n) { reader.readLine().toInt() } // 주차공간 별 무게 당 비용
            val carWeights = IntArray(1) + IntArray(m) { reader.readLine().toInt() } // 자동차 별 무게
            val pq = PriorityQueue<Int>().apply { addAll(1..n) }
            val q = ArrayDeque<Int>()
            var answer = 0

            for (i in 0 until 2 * m) {
                val carNumber = reader.readLine().toInt()
                if (carNumber > 0) { // 자동차가 들어옴
                    if (pq.isEmpty()) {
                        q.add(carNumber)
                        continue
                    }
                    val parkingSpaceNumber = pq.poll()
                    answer += parkingCosts[parkingSpaceNumber] * carWeights[carNumber]
                    carParkingNumbers[carNumber] = parkingSpaceNumber
                } else { // 자동차가 나감
                    pq.add(carParkingNumbers[-carNumber])
                    if (q.isNotEmpty()) {
                        while (pq.isNotEmpty()) {
                            val waitingCarNumber = q.removeFirst()
                            val parkingSpaceNumber = pq.poll()
                            answer += parkingCosts[parkingSpaceNumber] * carWeights[waitingCarNumber]
                            carParkingNumbers[waitingCarNumber] = parkingSpaceNumber
                        }
                    }
                }
            }
            writer.write("#$time $answer\n")
        }
    }
}
