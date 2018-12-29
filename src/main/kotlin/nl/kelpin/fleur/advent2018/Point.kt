package nl.kelpin.fleur.advent2018

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    constructor(string: String) : this(string.split(",").map(String::trim).map(String::toInt))
    constructor(list: List<Int>) : this(list[0], list[1])

    fun distanceTo(other: Point): Int = abs(other.x - x) + abs(other.y - y)
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

data class Point3D(val x: Int, val y: Int, val z: Int) {
    fun distanceTo(other: Point3D): Int = abs(other.x - x) + abs(other.y - y) + abs(other.z - z)
    fun plus(other: Point3D): Point3D = Point3D(x + other.x, y + other.y, z + other.z)
    fun minus(other: Point3D): Point3D = plus(other.times(-1))
    fun div(n: Int) = Point3D(x / n, y / n, z / n)
    fun times(n: Int): Point3D = Point3D(x * n, y * n, z * n)
    fun outer(other: Point3D): Point3D =Point3D(y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x)
}

data class PointN(val values: List<Int>) {
    fun distanceTo(other: PointN): Int =
            values.zip(other.values).sumBy{(a, b) -> abs(a - b)}
}