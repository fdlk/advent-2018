package nl.kelpin.fleur.advent2018

data class Point(val x: Int, val y: Int) {
    constructor(string: String) : this(string.split(",").map(String::trim).map(String::toInt))
    constructor(list: List<Int>) : this(list[0], list[1])

    fun distanceTo(other: Point): Int = Math.abs(other.x - x) + Math.abs(other.y - y)
    fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    fun times(n: Int): Point = Point(x * n, y * n)
    fun move(d: Direction): Point = when (d) {
        Up -> copy(y = y - 1)
        Left -> copy(x = x - 1)
        Down -> copy(y = y + 1)
        Right -> copy(x = x + 1)
    }
}

sealed class Direction
object Up : Direction()
object Down : Direction()
object Left : Direction()
object Right : Direction()
