package nl.kelpin.fleur.advent2018

class Day08(inputString: String) {
    val input: List<Int> = inputString.split(" ").map { it.trim().toInt() }
    val tree: Node = constructNode(0).second

    data class Node(val children: List<Node>, val metadatas: List<Int>) {
        fun metadataSum(): Int = metadatas.sum() + children.sumBy { it.metadataSum() }
        fun value(): Int = if (children.isEmpty()) metadatas.sum()
        else metadatas.sumBy { children.getOrNull(it - 1)?.value() ?: 0 }
    }

    fun constructChild(soFar: Pair<Int, List<Node>>, childIndex: Int): Pair<Int, List<Node>> {
        val (nextStartIndex, node) = constructNode(soFar.first)
        return Pair(nextStartIndex, soFar.second + node)
    }

    fun constructNode(startIndex: Int): Pair<Int, Node> {
        val (numberOfChildren, numberOfMetadatas) = input.drop(startIndex)
        val (nextStartIndex: Int, children: List<Node>) = (1..numberOfChildren).fold(
                Pair(startIndex + 2, emptyList()),
                ::constructChild
        )
        val nodeEndPos = nextStartIndex + numberOfMetadatas
        val metadatas = input.subList(nextStartIndex, nodeEndPos)
        return Pair(nodeEndPos, Node(children, metadatas))
    }

    fun part1(): Int = tree.metadataSum()

    fun part2(): Int = tree.value()
}