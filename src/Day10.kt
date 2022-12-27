import kotlin.math.abs

fun main() {

    fun countStrength(input: List<String>): Int {
        var nextCheck = 20
        var stepCount = 0
        var strength = 0
        var value = 1
        for (line in input) {
            val cmd = line.split(" ")
            if (nextCheck - stepCount <= 2) {
                strength += value*nextCheck
                nextCheck += 40
                if (nextCheck > 220) return strength
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
    val testOutput = listOf("##..##..##..##..##..##..##..##..##..##..",
    "###...###...###...###...###...###...###.",
    "####....####....####....####....####....",
    "#####.....#####.....#####.....#####.....",
    "######......######......######......####",
    "#######.......#######.......#######.....").joinToString("\n")
    check(countStrength(testInput) == 13140)
    check(render(testInput) == testOutput)

    val input = readInput("Day10")
    countStrength(input).println()
    render(input).println()
}