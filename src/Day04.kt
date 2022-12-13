fun main() {
    data class Range(val min: Int, val max: Int)

    fun getRanges(input: String): Array<Range> {
        val pairs = input.split(',')

        val range1 = Range(
            pairs[0].split('-')[0].toInt(), pairs[0].split('-')[1].toInt()
        )
        val range2 = Range(
            pairs[1].split('-')[0].toInt(), pairs[1].split('-')[1].toInt()
        )

        if (range1.min > range2.min) {
            return arrayOf(range2, range1)
        }
        return arrayOf(range1, range2)
    }

    fun part1(lines: List<String>): Int {
        var count = 0

        for (line in lines) {
            val ranges = getRanges(line)
            if (ranges[0].max < ranges[1].min) continue
            else if (ranges[0].min == ranges[1].min) count++
            else if (ranges[0].max >= ranges[1].max) count++
        }

        return count
    }

    fun part2(lines: List<String>): Int {
        var count = 0

        for (line in lines) {
            val ranges = getRanges(line)
            if (ranges[0].max < ranges[1].min) continue
            count++
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}