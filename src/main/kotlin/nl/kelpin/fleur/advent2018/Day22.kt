package nl.kelpin.fleur.advent2018

import arrow.syntax.function.memoize

sealed class Type(private val name: String) {
    abstract fun isCompatibleWith(tool: Tool): Boolean
    override fun toString(): String = name
}

object Rocky : Type("Rocky") {
    override fun isCompatibleWith(tool: Tool) = tool != Neither
}

object Wet : Type("Wet") {
    override fun isCompatibleWith(tool: Tool) = tool != Torch
}

object Narrow : Type("Narrow") {
    override fun isCompatibleWith(tool: Tool) = tool != ClimbingGear
}

sealed class Tool(private val name: String) {
    override fun toString(): String = name
}

object Neither : Tool("Neither")
object ClimbingGear : Tool("Climbing Gear")
object Torch : Tool("Torch")

data class State(val point: Point, val tool: Tool)

class Day22(val depth: Int, val target: Point) : Grid<State> {
    companion object {
        val mouth = Point(0, 0)
    }

    fun geologicIndex(point: Point): Int = with(point) {
        when {
            point == mouth -> 0
            point == target -> 0
            point.y == 0 -> point.x * 16807
            point.x == 0 -> point.y * 48271
            else -> erosion(Point(x - 1, y)) * erosion(Point(x, y - 1))
        }
    }

    val erosion = { point: Point -> (geologicIndex(point) + depth) % 20183 }.memoize()

    fun risk(point: Point) = erosion(point) % 3

    fun type(point: Point): Type = when (risk(point)) {
        0 -> Rocky
        1 -> Wet
        2 -> Narrow
        else -> throw IllegalStateException()
    }

    fun part1(): Int = (mouth.y..target.y).flatMap { y ->
        (mouth.x..target.x).map { x -> Point(x, y) }
    }.sumBy(::risk)

    private val costToMove = 1
    private val costToSwitchTool = 7

    override fun heuristicDistance(from: State, to: State): Int =
            from.point.distanceTo(to.point) * costToMove +
                    if (from.tool != to.tool) costToSwitchTool else 0

    fun getNeighbours(point: Point): List<Point> = with(point) {
        listOf(move(Down), move(Right), move(Left), move(Up))
                .filter { it.x >= 0 && it.y >= 0 }
    }

    override fun getNeighbours(state: State): List<State> = with(state) {
        val result = mutableListOf<State>()
        result.addAll(getNeighbours(point)
                .filter { type(it).isCompatibleWith(tool) }
                .map { state.copy(point = it) })
        result.addAll(listOf(Neither, ClimbingGear, Torch)
                .filter { it != tool }
                .filter { type(point).isCompatibleWith(it) }
                .map { state.copy(tool = it) })
        return result
    }

    override fun moveCost(from: State, to: State): Int =
            if (from.tool == to.tool) costToMove else costToSwitchTool

    fun part2(): Int = aStarSearch(State(mouth, Torch), State(target, Torch), this)
}