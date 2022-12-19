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

    fun addToQueues(piles: Array<LinkedList<Char>>, line: String): Boolean {
        if (line.contains("move")) {
            return false
        }

        val findings = charRegex.findAll(line)
        for (find in findings) {
            piles[find.range.first/columnWidth].addLast(line[find.range.first])
        }

        return true
    }

    fun move(piles: Array<LinkedList<Char>>, line: String) {
        val findings = numberRegex.findAll(line)
        val direction = Direction(findings.map { it.value }.toList())
        for (i in 1..direction.amount) {
            piles[direction.to-1].addFirst(piles[direction.from-1].pop())
        }
    }

    fun move2(piles: Array<LinkedList<Char>>, line: String) {
        val findings = numberRegex.findAll(line)
        val direction = Direction(findings.map { it.value }.toList())
        val toBeMoved = piles[direction.from-1].subList(0, direction.amount)
        piles[direction.to-1].addAll(0, toBeMoved)
        for (i in 0 until direction.amount) {
            piles[direction.from-1].removeAll(toBeMoved.toSet())
        }
    }

    fun moveCranes(input: List<String>, isPart2: Boolean = false): String {
        var isMapping = true
        var queuesNumber = 0
        for (line in input) {
            val findings  = numberRegex.findAll(line)
            if (findings.any() ) {
                queuesNumber = findings.last().value.toInt()
                break
            }
        }

        val piles = Array<LinkedList<Char>>(queuesNumber) { LinkedList() }

        for (line in input) {
            if (isMapping) {
                isMapping = addToQueues(piles, line)
            }

            if (!isMapping && !isPart2) {
                move(piles, line)
            } else if (!isMapping) {
                move2(piles, line)
            }
        }

        return piles.fold ("") { acc, it -> acc + it.pop().toString() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(moveCranes(testInput) == "CMZ")
    check(moveCranes(testInput, true) == "MCD")

    val input = readInput("Day05")
    moveCranes(input).println()
    moveCranes(input, true).println()
}