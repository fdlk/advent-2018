package nl.kelpin.fleur.advent2018

import nl.kelpin.fleur.advent2018.Day03.Claim.Companion.parse

class Day03(input: List<String>) {
    data class Claim(val id: Int, val xRange: IntRange, val yRange: IntRange) {
        companion object {
            val regex = Regex("""#(\d+) @ (\d+),(\d+): (\d+)x(\d+)""")
            fun parse(claimLine: String): Claim {
                val (id, topLeftX, topLeftY, width, height) =
                        regex.matchEntire(claimLine)!!.groupValues.drop(1).map(String::toInt)
                return Claim(id, topLeftX until topLeftX + width, topLeftY until topLeftY + height)
            }
        }

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