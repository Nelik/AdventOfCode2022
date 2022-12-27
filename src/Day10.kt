import java.util.LinkedList
import java.util.Queue
import kotlin.math.abs

fun main() {

    fun countStrength(input: List<String>): Int {
        val checkPoints: Queue<Int> = LinkedList(listOf(20, 60, 100, 140, 180, 220))
        var nextCheck = checkPoints.poll()
        var stepCount = 0
        var strength = 0
        var value = 1
        for (line in input) {
            val cmd = line.split(" ")
            if (nextCheck - stepCount <= 2) {
                strength += value*nextCheck
                nextCheck = checkPoints.poll()
                if (nextCheck == null) return strength
            }
            if (cmd.first() == "noop") {
                stepCount++
            } else {
                stepCount += 2
                value += cmd.last().toInt()
            }
        }
        return strength
    }

    fun draw(sprite: Int, stepCount: Int, result: String): String {
        return if (abs(sprite - stepCount%40) <= 1) {
            "$result#"
        } else "$result."
    }

    fun render(input: List<String>): String{
        var stepCount = 0
        var result = ""
        var sprite = 1
        for (line in input) {
            val cmd = line.split(" ")
            result = draw(sprite, stepCount, result)
            if (cmd.first() == "noop") {
                stepCount++
            } else {
                stepCount++
                result = draw(sprite, stepCount, result)
                stepCount++
                sprite += cmd.last().toInt()
            }
        }
        return result.chunked(40).joinToString("\n")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val testOutput = "##..##..##..##..##..##..##..##..##..##..\n###...###...###...###...###...###...###.\n####....####....####....####....####....\n#####.....#####.....#####.....#####.....\n######......######......######......####\n#######.......#######.......#######....."
    check(countStrength(testInput) == 13140)
    check(render(testInput) == testOutput)

    val input = readInput("Day10")
    countStrength(input).println()
    render(input).println()
}