import java.lang.RuntimeException

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 */
// 2022 카카오 인턴십 1번 문제 성격 유형 검사하기
// https://school.programmers.co.kr/learn/courses/30/lessons/118666
class Solution {
    fun solution(survey: Array<String>, choices: IntArray): String {
        // 각 옵션마다 짝이 있어 Pair를 사용하였고 원소마다 순서는 상관없지만 collection의 타입에 큰 영향이 없어 list를 사용하였습니다.
        val couplingList = listOf(
            'R' to 'T',
            'C' to 'F',
            'J' to 'M',
            'A' to 'N',
        )

        val metricHashMap = HashMap<Char, Int>()
        for (coupling in couplingList) {
            metricHashMap[coupling.first] = 0
            metricHashMap[coupling.second] = 0
        }

        // init with dummy, choices value 1부터 시작해서 0인덱스 블록
        // 하드코딩 말고 좀 더 이마를 탁 때릴 초기화법이 없을까요..?
        val scoreList = listOf(
            -1 to -1,
            3 to 0,
            2 to 0,
            1 to 0,
            0 to 0,
            0 to 1,
            0 to 2,
            0 to 3,
        )

        calculateSurvey(survey, scoreList, choices, metricHashMap)

        return analyzePersonality(couplingList, metricHashMap)
    }

    // 입력 받은 설문과 choices를 통해 metricHashMap에 점수를 계산합니다.
    private fun calculateSurvey(
        survey: Array<String>,
        scoreList: List<Pair<Int, Int>>,
        choices: IntArray,
        metricHashMap: HashMap<Char, Int>,
    ) {
        for (idx in survey.indices) {
            val (lhs, rhs) = survey[idx].parsePairChar()
            val (negaScore, posiScore) = scoreList[choices[idx]]
            metricHashMap[lhs] = metricHashMap.getSafeValue(lhs) + negaScore
            metricHashMap[rhs] = metricHashMap.getSafeValue(rhs) + posiScore
        }
    }

    // 계산이 끝난 metricHashMap을 통해 성격 유형을 분석합니다.
    private fun analyzePersonality(
        couplingList: List<Pair<Char, Char>>,
        metricHashMap: HashMap<Char, Int>,
    ): String {
        var answer: String = ""
        for (coupling in couplingList) {
            val (lhs, rhs) = coupling
            val lhsScore = metricHashMap.getSafeValue(lhs)
            val rhsScore = metricHashMap.getSafeValue(rhs)
            answer += if (lhsScore >= rhsScore) {
                lhs
            } else {
                rhs
            }
        }
        return answer
    }

    // 입력 데이터의 형태가 "RN" 과 같이 문자 두 개가 연속한 문자열이여서 확장함수를 만들었습니다.
    fun String.parsePairChar(): Pair<Char, Char> {
        return this[0] to this[1]
    }

    // metricHashMap이 nullable할 수 있다 경고가 뜨는 것 같습니다.(입력 키에 해당하는 값을 찾지 못하여)
    // 이 문제에선 유효한 상황만 주어지기에 safe하다 가정하여 다음 확장함수를 만들었습니다.
    fun HashMap<Char, Int>.getSafeValue(key: Char): Int {
        return this[key] ?: throw RuntimeException()
    }
}
