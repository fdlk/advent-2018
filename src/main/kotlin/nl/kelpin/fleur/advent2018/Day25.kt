package nl.kelpin.fleur.advent2018

class Day25(input: List<String>) {
    companion object {
        fun parse(line: String): PointN = PointN(line.split(",").map(String::trim).map(String::toInt))
    }

    private val stars: Set<PointN> = input.map(::parse).toSet()
    private fun PointN.isWithinRange(point: PointN) = distanceTo(point) <= 3

    tailrec fun part1(starsLeft: Set<PointN> = stars, found: Set<Set<PointN>> = emptySet()): Int =
            if (starsLeft.isEmpty())
                found.size
            else {
                val star = starsLeft.first()
                val (containing, notContaining) = found.split { constellation -> constellation.any { star.isWithinRange(it) } }
                val mergedConstellation = containing.flatten().toSet() + star
                part1(starsLeft - star, notContaining.plusElement(mergedConstellation))
            }
}