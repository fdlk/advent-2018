package nl.kelpin.fleur.advent2018

class Day08(inputString: String) {
    val input: List<Int> = inputString.split(" ").map { it.trim().toInt() }

    data class NodeInfo(val endPosition: Int = 0, val metadataSum: Int = 0)

    fun addChildInfo(soFar: NodeInfo, childIndex: Int): NodeInfo {
        val next = sumMetadatas(soFar.endPosition)
        return next.copy(metadataSum = next.metadataSum + soFar.metadataSum)
    }

    fun sumMetadatas(startIndex: Int): NodeInfo {
        val (numberOfChildren, numberOfMetadatas) = input.drop(startIndex)
        val childInfo: NodeInfo = (1..numberOfChildren).fold(
                NodeInfo(startIndex + 2, 0),
                ::addChildInfo
        )
        val nodeEndPos = childInfo.endPosition + numberOfMetadatas
        val metadatas = input.subList(childInfo.endPosition, nodeEndPos)
        return NodeInfo(nodeEndPos, childInfo.metadataSum + metadatas.sum())
    }

    fun part1(): Int = sumMetadatas(0).metadataSum
}