import java.util.LinkedList
import java.util.Queue

fun main() {

    fun findChar(c: Char, input: List<String>) = input
        .mapIndexed{ y, line -> line.mapIndexed {x, _ -> y to x} }
        .flatten()
        .filter{ (y, x) -> input[y][x] == c }
        .toList()

    fun bfs(field: List<String>, start: List<Pair<Int, Int>>, target: Pair<Int, Int>): Int {
        val queue: Queue<Pair<Int, Int>> = LinkedList(start)
        val visited: MutableMap<Pair<Int, Int>, Int> = queue.associateWith { 0 }.toMutableMap()
        val directions = listOf(-1 to 0, 1 to 0, 0 to 1, 0 to -1)
        while(queue.isNotEmpty()) {
            val (y, x) = queue.poll()
            directions
                .map{ (dirY, dirX) -> Pair(y + dirY, x + dirX) }
                .filter{ (y2, x2) -> y2 in field.indices && x2 in field[0].indices && field[y2][x2] <= field[y][x] + 1 && !visited.contains(Pair(y2, x2))}
                .forEach{
                    visited[it] = visited[Pair(y, x)]!! + 1
                    queue.add(it)
                    if(it == target) return visited[it]!!
                }
        }

        return visited[target]!!
    }

    fun countSteps(input: List<String>): Int {
        // init map
        val target = findChar('E', input).first()
        val start = findChar('S', input)
        val map = input.map{it.replace('S', 'a').replace('E', 'z')}

        return bfs(map, start, target)
    }

    fun countSteps2(input: List<String>): Int {
        // init map
        val target = findChar('E', input).first()
        val start = findChar('S', input) + findChar('a', input)
        val map = input.map{it.replace('S', 'a').replace('E', 'z')}

        return bfs(map, start, target)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(countSteps(testInput) == 31)
    check(countSteps2(testInput) == 29)

    val input = readInput("Day12")
    countSteps(input).println()
    countSteps2(input).println()
}