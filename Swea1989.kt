package ps.ps

/**
 * @author : Unagi_zoso
 * @date : 2023-11-07
 */
// https://swexpertacademy.com/main/code/problem/problemDetail.do

/**
 * Palindrome은 데칼코마니처럼 끝에서부터 가운데 문자가 같은 문자를 말한다.
 * ex ) level,eye
 */
fun main() = System.`in`.bufferedReader().use { reader ->
    System.out.bufferedWriter().use { writer ->
        for (turn in 1..(reader.readLine().toInt())) {
            val inputStr = reader.readLine().trimEnd()
            writer.write("#$turn ${isPalindrome(inputStr)}\n")
        }
    }
}

/**
 * palindrome일 시 1
 * 아닐 시 0을 반환한다.
 */
private fun isPalindrome(inputStr: String): Int {
    for (i in 0 until (inputStr.length / 2)) {
        if (inputStr[i] != inputStr[inputStr.length - i - 1]) {
            return 0
        }
    }
    return 1
}
