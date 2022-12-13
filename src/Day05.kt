import java.util.*

fun main() {
    val columnWidth = 4
    val charRegex = "[A-Z]".toRegex()
    val numberRegex = "[0-9]+".toRegex()

    class Direction (directions: List<String>) {
        val amount: Int
        val from: Int
        val to: Int
        init {
            amount = directions[0].toInt()
            from = directions[1].toInt()
            to = directions[2].toInt()
        }
    }

    fun addToQueues(stacks: Array<Stack<Char>>, line: String): Boolean {
        if (line.contains("move")) {
            return false
        }

        val findings = charRegex.findAll(line)
        for (find in findings) {
            stacks[find.range.first/columnWidth].add(line[find.range.first])
        }

        return true
    }

    fun move(stacks: Array<Stack<Char>>, line: String) {
        val findings = numberRegex.findAll(line)
        val direction = Direction(findings.map { it.value }.toList())
        for (i in 1..direction.amount) {
            stacks[direction.to-1].add(stacks[direction.from-1].pop())
        }
    }

    fun part1(input: List<String>): String {
        var isMapping = true
        var queuesNumber = 0
        for (line in input) {
            val findings  = numberRegex.findAll(line)
            if (findings.any() ) {
                queuesNumber = findings.last().value.toInt()
                break
            }
        }

        val stacks = Array<Stack<Char>>(queuesNumber) { Stack() }

        for (line in input) {
            if (isMapping) {
                isMapping = addToQueues(stacks, line)
            }

            if (!isMapping) {
                move(stacks, line)
            }
        }

        return stacks.map { it.pop() }.toString()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
//    check(part2(testInput) == 4)

    val input = readInput("Day05")
    part1(input).println()
//    part2(input).println()
}