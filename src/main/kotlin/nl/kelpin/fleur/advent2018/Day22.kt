package nl.kelpin.fleur.advent2018

import arrow.syntax.function.memoize

sealed class Type
object Rocky : Type() {
    override fun toString(): String {
        return "Rocky"
    }
}

object Wet : Type() {
    override fun toString(): String {
        return "Wet"
    }
}

object Narrow : Type() {
    override fun toString(): String {
        return "Narrow"
    }
}

class Day22(val depth: Int, val target: Point) {
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
}