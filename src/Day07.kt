import java.lang.Integer.min

fun main() {

    class TreeNode(var value:Int = 0, val name: String){
        var parent:TreeNode? = null

        var children:MutableList<TreeNode> = mutableListOf()

        fun addChild(node:TreeNode){
            children.add(node)
            node.parent = this
        }

        override fun toString(): String {
            var s = "$name: $value"
            if (children.isNotEmpty()) {
                s += " {" + children.map { it.toString() } + " }"
            }
            return s
        }
    }

    fun createTree(input: List<String>): TreeNode {
        val treeRoot = TreeNode(name = "/")
        var currentNode = treeRoot
        for (line in input) {
            if (line[0] == '$') {
                if (line == "$ cd /") continue
                if (line == "$ cd ..") {
                    currentNode = currentNode.parent!!
                } else if (line.contains("$ cd")) {
                    val cmdParts = line.split(" ")
                    currentNode = currentNode.children.find { it.name == cmdParts.last() }!!
                }
            } else {
                if (line.contains("dir ")) {
                    currentNode.addChild(TreeNode(name = line.split(" ").last()))
                } else {
                    val fileParts = line.split(" ")
                    currentNode.addChild(TreeNode(fileParts.first().toInt(), fileParts.last()))
                }
            }
        }

        return treeRoot
    }

    fun countNodesValue(node: TreeNode): Int {
        return if (node.value != 0) node.value
        else {
            node.value = node.children.sumOf { countNodesValue(it) }
            node.value
        }
    }

    fun getSum(node: TreeNode, maxSize: Int): Int {
        return if (node.children.isEmpty()) 0
        else if (node.value > maxSize) 0 + node.children.sumOf { getSum(it, maxSize) }
        else node.value + node.children.sumOf { getSum(it, maxSize) }
    }


    fun findSum(input: List<String>, largeSize: Int = 100000): Int {
        val tree = createTree(input)
        countNodesValue(tree)
        return getSum(tree, largeSize)
    }

    fun getDirSize(node: TreeNode, minToDelete: Int, minValue: Int): Int {
        return if (node.children.isEmpty()) minValue
        else if (node.value in minToDelete until minValue) min(node.value, node.children.minOf { getDirSize(it, minToDelete, minValue) })
        else min(minValue, node.children.minOf { getDirSize(it, minToDelete, minValue) })
    }

    fun findDirSize(input: List<String>, diskSpace: Int = 70000000, neededSpace: Int = 30000000): Int {
        val tree = createTree(input)
        countNodesValue(tree)
        val minToDelete = neededSpace - (diskSpace - tree.value)
        return getDirSize(tree, minToDelete, tree.value)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(findSum(testInput) == 95437)
    check(findDirSize(testInput) == 24933642)

    val input = readInput("Day07")
    findSum(input).println()
    findDirSize(input).println()
}