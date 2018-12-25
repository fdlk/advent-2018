package nl.kelpin.fleur.advent2018

class Day25(input: List<String>) {
    companion object {
        fun parse(line: String): PointN =
                PointN(line.split(",").map(String::trim).map(String::toInt))
    }

    private val stars: Set<PointN> = input.map(::parse).toSet()
    private fun PointN.isWithinRange(point: PointN) = distanceTo(point) <= 3

    private tailrec fun remove(constellation: Set<PointN>, toTest: Set<PointN>, testedOutside: Set<PointN>): Set<PointN> =
            if (toTest.isEmpty()) testedOutside
            else {
                val star = toTest.first()
                if (constellation.any { star.isWithinRange(it) })
                    remove(constellation + star, toTest - star + testedOutside, emptySet())
                else
                    remove(constellation, toTest - star, testedOutside + star)
            }

    tailrec fun part1(starsLeft: Set<PointN> = stars, constellationsFound: Int = 0): Int =
            if (starsLeft.isEmpty()) constellationsFound
            else {
                val star = starsLeft.first()
                part1(remove(setOf(star), starsLeft - star, emptySet()),
                        constellationsFound + 1)
            }
}