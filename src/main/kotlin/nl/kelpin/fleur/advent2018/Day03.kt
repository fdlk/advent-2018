package nl.kelpin.fleur.advent2018

import nl.kelpin.fleur.advent2018.Day03.Claim.Companion.parse

class Day03(input: List<String>) {
    data class Claim(val id: String, val topLeftX: Int, val topLeftY: Int, val width: Int, val height: Int) {
        companion object {
            fun parse(claimLine: String): Claim {
                val values = (Regex("""(#\d+) @ (\d+),(\d+): (\d+)x(\d+)""").matchEntire(claimLine))!!.groupValues
                return Claim(values[1], values[2].toInt(), values[3].toInt(), values[4].toInt(), values[5].toInt())
            }
        }
        val xRange = topLeftX until topLeftX + width
        val yRange = topLeftY until topLeftY + height
        fun contains(x: Int, y: Int): Boolean = xRange.contains(x) && yRange.contains(y)
    }

    private val claims = input.map(::parse)
    private val contested: List<Pair<Int, Int>> =
            (0..999).flatMap { x ->
                (0..999).mapNotNull { y ->
                    if (claims.filter { it.contains(x, y) }.count() >= 2)
                        Pair(x, y)
                    else null
                }
            }

    fun part1() = contested.size
    fun part2() = claims.find { claim -> contested.none { claim.contains(it.first, it.second) } }?.id
}