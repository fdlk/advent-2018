package nl.kelpin.fleur.advent2018

import java.util.*

interface Grid<State> {
    fun heuristicDistance(from: State, to: State): Int
    fun getNeighbours(state: State): List<State>
    fun moveCost(from: State, to: State): Int
}

fun <State> aStarSearch(start: State,
                        finish: State,
                        grid: Grid<State>): Int {
    val closedVertices = mutableSetOf<State>()
    val costFromStart = mutableMapOf(start to 0)
    val estimatedTotalCost = mutableMapOf(start to grid.heuristicDistance(start, finish))
    val openVertices = PriorityQueue<State>(compareBy { estimatedTotalCost[it] }).also { it.add(start) }

    while (openVertices.size > 0) {
        val currentPos = openVertices.poll()
        if (currentPos == finish) return estimatedTotalCost[currentPos]!!
        closedVertices.add(currentPos)
        grid.getNeighbours(currentPos)
                .filterNot { closedVertices.contains(it) }
                .forEach { neighbour ->
                    val score = costFromStart.getValue(currentPos) + grid.moveCost(currentPos, neighbour)
                    if (score < costFromStart.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                        costFromStart.put(neighbour, score)
                        estimatedTotalCost.put(neighbour, score + grid.heuristicDistance(neighbour, finish))
                        // remove and add because its priority changed
                        openVertices.remove(neighbour)
                        openVertices.add(neighbour)
                    }
                }
    }

    throw IllegalArgumentException("No Path from Start $start to Finish $finish")
}