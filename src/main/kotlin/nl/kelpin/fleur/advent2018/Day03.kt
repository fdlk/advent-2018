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

        fun squares(): List<Pair<Int, Int>> = xRange.flatMap { x -> yRange.map { y -> Pair(x, y) } }
        fun intersects(other: Claim): Boolean = xRange.overlaps(other.xRange) && yRange.overlaps(other.yRange)
    }

    private val claims = input.map(::parse)

    fun part1() = claims.flatMap(Claim::squares).groupBy { it }.count { it.value.size > 1 }
    fun part2() = claims.find { claim -> claims.filter { it != claim }.none { claim.intersects(it) } }?.id
}