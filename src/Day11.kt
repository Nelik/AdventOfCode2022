import java.util.LinkedList
import java.util.Queue

fun main() {
    class Monkey(var countItems: Int = 0) {
        lateinit var startingItems: Queue<Int>
        lateinit var operation: (Long) -> Long
        var divisibleBy: Int = 1
        var ifTrue: Int = 0
        var ifFalse: Int = 0
    }

    fun countMonkeyBusinessLevel(input: List<String>, rounds: Int = 20, worryLevel: Int = 3): Long {
        val monkeyInputs = input.chunked(7)
        val monkeys: MutableList<Monkey> = mutableListOf()
        val itemsKey = "Starting items: "
        val operationKey = "Operation: new = old "
        val testKey = "Test: divisible by "
        val ifTrueKey = "If true: throw to monkey "
        val ifFalseKey = "If false: throw to monkey "

        // Build monkeys description
        for (monkeyDescription in monkeyInputs) {
            val monkey = Monkey()
            for (line in monkeyDescription) {
                if (line.contains(itemsKey)) {
                    monkey.startingItems = LinkedList(line.split(itemsKey).last().split(", ").map { it.toInt() }.toList())
                } else if (line.contains(operationKey)) {
                    val operationParts = line.split(operationKey).last().split(" ")
                    if (operationParts.first() == "+" && operationParts.last() == "old") {
                        monkey.operation = {a -> a + a}
                    } else if (operationParts.first() == "*" && operationParts.last() == "old") {
                        monkey.operation = {a -> a*a}
                    } else if (operationParts.first() == "+") {
                        monkey.operation = {a -> a + operationParts.last().toInt()}
                    } else {
                        monkey.operation = { a -> a * operationParts.last().toInt() }
                    }
                } else if (line.contains(testKey)) {
                    monkey.divisibleBy = line.split(testKey).last().toInt()
                } else if (line.contains(ifTrueKey)) {
                    monkey.ifTrue = line.split(ifTrueKey).last().toInt()
                } else if (line.contains(ifFalseKey)) {
                    monkey.ifFalse = line.split(ifFalseKey).last().toInt()
                }
            }
            monkeys.add(monkey)
        }

        val gcd = monkeys.map{it.divisibleBy}.reduce { x, y -> x*y}

        // do rounds
        repeat (rounds) {
            for (monkey in monkeys) {
                while ( monkey.startingItems.isNotEmpty() ) {
                    val item = monkey.startingItems.poll()
                    monkey.countItems++
                    // %gcd won't affect the test and will keep items in Int range (value won't be bigger then gcd)
                    val value = (monkey.operation(item.toLong())%gcd/worryLevel).toInt()
                    val target = if (value%monkey.divisibleBy== 0) monkey.ifTrue else monkey.ifFalse
                    monkeys[target].startingItems.add(value)
                }
            }
        }

        monkeys.sortByDescending { it.countItems }
        return monkeys.first().countItems.toLong() * monkeys[1].countItems.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(countMonkeyBusinessLevel(testInput) == 10605.toLong())
    check(countMonkeyBusinessLevel(testInput, 10000, 1) == 2713310158)

    val input = readInput("Day11")
    countMonkeyBusinessLevel(input).println()
    countMonkeyBusinessLevel(input, 10000, 1).println()
}