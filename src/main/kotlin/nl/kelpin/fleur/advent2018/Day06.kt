package nl.kelpin.fleur.advent2018

class Day06(input: List<String>) {
    data class Point(val x: Int, val y: Int) {
        constructor(list: List<Int>) : this(list[0], list[1])
    }

    val points: List<Point> = input.map { Point(it.split(",").map { it.trim().toInt() }) }

}