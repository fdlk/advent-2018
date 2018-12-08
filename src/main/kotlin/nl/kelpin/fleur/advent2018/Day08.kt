package nl.kelpin.fleur.advent2018

class Day08(inputString: String) {
    val input: Iterator<Int> = inputString.split(" ").map { it.trim().toInt() }.asSequence().iterator()
    val tree: Node = constructNode()

    data class Node(val children: List<Node>, val metadatas: List<Int>) {
        fun metadataSum(): Int = metadatas.sum() + children.sumBy { it.metadataSum() }
        fun value(): Int = if (children.isEmpty()) metadatas.sum()
        else metadatas.map { children.getOrNull(it - 1) }.filterNotNull().map { it.value() }.sum()
    }

    fun constructNode(): Node {
        val numberOfChildren = input.next()
        val numberOfMetadatas = input.next()
        val children = (1..numberOfChildren).map { constructNode() }
        val metadatas = (1..numberOfMetadatas).map { input.next() }
        return Node(children, metadatas)
    }

    fun part1(): Int = tree.metadataSum()

    fun part2(): Int = tree.value()
}