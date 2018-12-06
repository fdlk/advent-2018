package nl.kelpin.fleur.advent2018

data class Point(val x: Int, val y: Int) {
    constructor(string: String) : this(string.split(",").map(String::trim).map(String::toInt))
    constructor(list: List<Int>) : this(list[0], list[1])
    fun distanceTo(other: Point): Int = Math.abs(other.x - x) + Math.abs(other.y - y)
}