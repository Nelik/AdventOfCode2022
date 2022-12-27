import kotlin.math.abs

fun main() {
    fun moveTail(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        // need to move
        if (abs(head.first - tail.first) == 2 || abs(head.second - tail.second) == 2) {
            // diagonal move
            if (head.first != tail.first && head.second != tail.second) {
                if (head.first > tail.first && head.second > tail.second) return Pair(tail.first + 1, tail.second + 1)
                if (head.first > tail.first) return Pair(tail.first + 1, tail.second - 1)
                if (head.second > tail.second) return Pair(tail.first - 1, tail.second + 1)
                return Pair(tail.first - 1,tail.second - 1)
            }

            // move up
            if(head.first > tail.first) return Pair(tail.first + 1, tail.second)
            // move down
            if(head.first < tail.first) return Pair(tail.first - 1, tail.second)
            //move right
            if (head.second > tail.second) return Pair(tail.first, tail.second + 1)
            //move left
            if (head.second < tail.second) return Pair(tail.first, tail.second - 1)
        }

        return tail
    }

    fun countSteps(input: List<String>): Int {
        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
        var head = Pair(0, 0)
        var tail = Pair(0, 0)
        visited.add(tail)

        for (line in input) {
            val direction = line.split(" ").first()
            val steps = line.split(" ").last().toInt()

            for(i in 1..steps) {
                if (direction == "L") head = Pair(head.first, head.second - 1)
                if (direction == "R") head = Pair(head.first, head.second + 1)
                if (direction == "D") head = Pair(head.first + 1, head.second)
                if (direction == "U") head = Pair(head.first - 1, head.second)
                tail = moveTail(head, tail)
                visited.add(Pair(tail.first, tail.second))
            }
        }

        return visited.size
    }

    fun move(rope: Array<Pair<Int, Int>>): Pair<Int, Int> {
        for (i in 1 until rope.size) {
            val head = rope[i-1]
            val tail = rope[i]
            // need to move
            if (abs(head.first - tail.first) == 2 || abs(head.second - tail.second) == 2) {
                // diagonal move
                if (head.first != tail.first && head.second != tail.second) {
                    if (head.first > tail.first && head.second > tail.second) {
                        rope[i] = Pair(tail.first + 1, tail.second + 1)
                        continue
                    }
                    if (head.first > tail.first) {
                        rope[i] =  Pair(tail.first + 1, tail.second - 1)
                        continue
                    }
                    if (head.second > tail.second) {
                        rope[i] =  Pair(tail.first - 1, tail.second + 1)
                        continue
                    }
                    rope[i] = Pair(tail.first - 1,tail.second - 1)
                    continue
                }

                // move up
                if(head.first > tail.first) {
                    rope[i] = Pair(tail.first + 1, tail.second)
                    continue
                }
                // move down
                if(head.first < tail.first) {
                    rope[i] = Pair(tail.first - 1, tail.second)
                    continue
                }
                //move right
                if (head.second > tail.second) {
                    rope[i] = Pair(tail.first, tail.second + 1)
                    continue
                }
                //move left
                if (head.second < tail.second) {
                    rope[i] = Pair(tail.first, tail.second - 1)
                    continue
                }
            }
        }

        return Pair(rope.last().first, rope.last().second)
    }

    fun countLongRopeSteps(input: List<String>): Int {
        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
        val rope = Array(10){Pair(0, 0)}

        visited.add(rope[0])

        for (line in input) {
            val direction = line.split(" ").first()
            val steps = line.split(" ").last().toInt()

            for(i in 1..steps) {
                if (direction == "L") rope[0] = Pair(rope[0].first, rope[0].second - 1)
                if (direction == "R") rope[0] = Pair(rope[0].first, rope[0].second + 1)
                if (direction == "D") rope[0] = Pair(rope[0].first + 1, rope[0].second)
                if (direction == "U") rope[0] = Pair(rope[0].first - 1, rope[0].second)
                val lastKnot = move(rope)
                visited.add(lastKnot)
            }
        }

        return visited.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(countSteps(testInput) == 13)
    val testInput2 = readInput("Day09_test2")
    check(countLongRopeSteps(testInput2) == 36)

    val input = readInput("Day09")
    countSteps(input).println()
    countLongRopeSteps(input).println()
}