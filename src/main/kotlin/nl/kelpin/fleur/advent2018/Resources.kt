package nl.kelpin.fleur.advent2018

import java.io.File

internal object Resources

fun resourceAsString(fileName: String, delimiter: String = ""): String =
        File(Resources.javaClass.classLoader.getResource(fileName).toURI())
                .readLines()
                .reduce { a, b -> "$a$delimiter$b" }

fun resourceAsList(fileName: String): List<String> =
        File(Resources.javaClass.classLoader.getResource(fileName).toURI())
                .readLines()

data class Point(val x: Int, val y: Int) {
    constructor(list: List<Int>) : this(list[0], list[1])
    fun distanceTo(other: Point): Int = Math.abs(other.x - x) + Math.abs(other.y - y)
}