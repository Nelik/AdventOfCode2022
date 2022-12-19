import java.util.*

fun main() {
    fun init(input: String, window: LinkedList<Char>, windowSize: Int) {
        for (i in 0 until windowSize) {
            window.add(input[i])
        }
    }

    fun findMarker(input: String, windowSize: Int = 4): Int {
        val window: LinkedList<Char> = LinkedList()
        init(input, window, windowSize)
        for(i in windowSize until input.length) {
            val windowSet = window.toSet()
            if (windowSet.size == windowSize) {
                return i
            } else {
                window.pop()
                window.add(input[i])
            }
        }
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readStream("Day06_test")
    check(findMarker(testInput) == 7)
    check(findMarker(testInput, 14) == 19)

    val input = readStream("Day06")
    findMarker(input).println()
    findMarker(input, 14).println()
}