
fun main() {

    class TreeNode(val value:Int = 0, val name: String){
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
        return TreeNode(name = "/")
    }

    fun findSum(input: List<String>): Int {
        return 7
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(findSum(testInput) == 7)
//    check(findMarker(testInput, 14) == 19)
//
//    val input = readStream("Day06")
//    findMarker(input).println()
//    findMarker(input, 14).println()
}