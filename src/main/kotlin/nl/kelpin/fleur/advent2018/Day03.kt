package nl.kelpin.fleur.advent2018

class Day03(input: List<String>) {
    companion object {
        fun parse(claimLine: String): Claim {
            val values = (Regex("""(#\d+) @ (\d+),(\d+): (\d+)x(\d+)""").matchEntire(claimLine))!!.groupValues
            return Claim(values[1], values[2].toInt(), values[3].toInt(), values[4].toInt(), values[5].toInt())
        }
    }
    data class Claim(val id: String, val topLeftX: Int, val topLeftY: Int, val width: Int, val height: Int){
        val xRange = topLeftX until topLeftX + width
        val yRange = topLeftY until topLeftY + height
        fun contains(x: Int, y: Int): Boolean = xRange.contains(x) && yRange.contains(y)
    }
    val claims = input.map(::parse)

    fun part1() =
        (0..999).map { x ->
            (0..999).count { y ->
                claims.filter { it.contains(x,y) } .take(2).count() == 2
            }
        }.sum()
}