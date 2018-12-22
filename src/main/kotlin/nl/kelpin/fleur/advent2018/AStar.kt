package nl.kelpin.fleur.advent2018

import java.util.*

interface  Grid<State> {
    fun heuristicDistance(from: State, to: State): Int
    fun getNeighbours(state: State): List<State>
    fun moveCost(from: State, to: State): Int
}

fun <State> aStarSearch(start: State,
                        finish: State,
                        grid: Grid<State>): Int {
    fun findCost(currentPos: State, cameFrom: Map<State, State>): Int {
        var cost = 0
        var current = currentPos
        while (cameFrom.containsKey(current)) {
            val to = cameFrom.getValue(current)
            cost += grid.moveCost(current, to)
            current = to
        }
        return cost
    }

    val closedVertices = mutableSetOf<State>()
    val costFromStart = mutableMapOf(start to 0)
    val estimatedTotalCost = mutableMapOf(start to grid.heuristicDistance(start, finish))
    val openVertices = PriorityQueue<State>(compareBy { estimatedTotalCost[it] })
    val cameFrom = mutableMapOf<State, State>()  // Used to generate path by back tracking
    openVertices.add(start)

    while (openVertices.size > 0) {
        val currentPos = openVertices.poll()

        if (currentPos == finish) {
            return findCost(currentPos, cameFrom)
        }
        closedVertices.add(currentPos)

        grid.getNeighbours(currentPos)
                .filterNot { closedVertices.contains(it) }
                .forEach { neighbour ->
                    val score = costFromStart.getValue(currentPos) + grid.moveCost(currentPos, neighbour)
                    if (score < costFromStart.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                        cameFrom.put(neighbour, currentPos)
                        costFromStart.put(neighbour, score)
                        estimatedTotalCost.put(neighbour, score + grid.heuristicDistance(neighbour, finish))
                        // remove and add because its priority changes
                        openVertices.remove(neighbour)
                        openVertices.add(neighbour)
                    }
                }
    }

    throw IllegalArgumentException("No Path from Start $start to Finish $finish")
}