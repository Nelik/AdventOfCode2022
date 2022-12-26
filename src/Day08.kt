import java.lang.Integer.max

fun main() {

    data class Tree(val left: Int = 0, val right: Int = 0, val up: Int = 0, val down: Int = 0, val height: Int = 0)

    fun maxL(tree: Tree): Int {
        return max(tree.left, tree.height)
    }

    fun maxU(tree: Tree): Int {
        return max(tree.up, tree.height)
    }

    fun maxR(tree: Tree): Int {
        return max(tree.right, tree.height)
    }

    fun maxD(tree: Tree): Int {
        return max(tree.down, tree.height)
    }

    fun countVisibleTrees(input: List<String>): Int {
        val rows = input.size
        val columns = input[0].length
        var visibleTrees = rows*2 + columns*2 - 4
        val map: Array<Array<Tree>> = Array(rows) {Array(columns) {Tree()}}
        // walk through top left
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                val height = input[i][j].digitToInt()
                if (i == 0 && j == 0) {
                    map[i][j] = Tree(0, 0, 0, 0, height)
                } else if (i == 0) {
                    map[i][j] = Tree(maxL(map[i][j-1]), 0, 0, 0, height)
                } else if (j == 0) {
                    map[i][j] = Tree(0, 0, maxU(map[i-1][j]), 0, height)
                } else {
                    map[i][j] = Tree(maxL(map[i][j-1]), 0, maxU(map[i-1][j]), 0, height)
                }
            }
        }

        // walk through bottom right
        for (i in rows - 1 downTo 0 ) {
            for (j in columns - 1 downTo 0) {
                val height = map[i][j].height
                if (i == rows - 1 && j == columns - 1) {
                    map[i][j] = Tree(map[i][j].left, 0, map[i][j].up, 0, height)
                } else if (i == rows - 1) {
                    map[i][j] = Tree(map[i][j].left, maxR(map[i][j+1]), map[i][j].up, 0, height)
                } else if (j == columns - 1) {
                    map[i][j] = Tree(map[i][j].left, 0, map[i][j].up, maxD(map[i+1][j]), height)
                } else {
                    map[i][j] = Tree(map[i][j].left, maxR(map[i][j+1]), map[i][j].up, maxD(map[i+1][j]), height)
                    if (i != 0 && j != 0) {
                        if (height > map[i][j].left || height > map[i][j].right ||
                            height > map[i][j].up || height > map[i][j].down) {
                            visibleTrees++
                        }
                    }
                }
            }
        }

        return visibleTrees
    }

    fun goLeft(input: List<String>, row: Int, column: Int, height: Int, viewScore: Int = 1): Int {
        if (column == 0 || input[row][column].digitToInt() >= height) return viewScore
        if (column - 1 == 0) return viewScore + 1
        return goLeft(input, row, column - 1, height, viewScore + 1)
    }

    fun goRight(input: List<String>, row: Int, column: Int, height: Int, viewScore: Int = 1): Int {
        if (column == input[0].length - 1  || input[row][column].digitToInt() >= height) return viewScore
        if (column + 1 == input[0].length - 1 ) return viewScore + 1
        return goRight(input, row, column + 1, height, viewScore + 1)
    }

    fun goUp(input: List<String>, row: Int, column: Int, height: Int, viewScore: Int = 1): Int {
        if (row == 0 || input[row][column].digitToInt() >= height) return viewScore
        if (row - 1 == 0 ) return viewScore + 1
        return goUp(input, row - 1 , column, height, viewScore + 1)
    }

    fun goDown(input: List<String>, row: Int, column: Int, height: Int, viewScore: Int = 1): Int {
        if (row == input.size - 1 || input[row][column].digitToInt() >= height) return viewScore
        if (row + 1 == input.size - 1) return viewScore + 1
        return goDown(input, row + 1 , column, height, viewScore + 1)
    }

    fun countMaxViewingScore(input: List<String>): Int {
        val rows = input.size
        val columns = input[0].length
        var maxViewingScore = 0
        for (i in 1 until rows - 1) {
            for (j in 1 until columns - 1 ) {
                val height = input[i][j].digitToInt()
                val left = goLeft(input, i, j-1, height)
                val right = goRight(input, i, j+1, height)
                val up = goUp(input, i-1, j, height)
                val down = goDown(input, i+1, j, height)
                maxViewingScore = max(maxViewingScore, left*right*up*down)
            }
        }
        return maxViewingScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(countVisibleTrees(testInput) == 21)
    check(countMaxViewingScore(testInput) == 8)

    val input = readInput("Day08")
    countVisibleTrees(input).println()
    countMaxViewingScore(input).println()
}