package nl.kelpin.fleur.advent2018

class Day06(input: List<String>) {
    val points: List<Point> = input.map { Point(it) }
    val xRange: IntRange = points.map { it.x }.range()
    val yRange: IntRange = points.map { it.y }.range()
    private val pointsWithInfiniteArea: Set<Point> =
            rectangle().filter(::isOnTheEdge).mapNotNull(::closestPoint).toSet()

    fun closestPoint(p: Point): Point? {
        val (a, b) = points.sortedBy { it.distanceTo(p) }
        return if (a.distanceTo(p) == b.distanceTo(p)) null else a
    }
    fun totalDistance(p: Point): Int = points.sumBy { it.distanceTo(p) }
    private fun rectangle(): Iterable<Point> = xRange.flatMap { x -> yRange.map { y -> Point(x, y) } }
    private fun isOnTheEdge(p: Point): Boolean =
            p.x == xRange.start || p.x == xRange.endInclusive || p.y == yRange.start || p.y == yRange.endInclusive

    fun part1(): Int? = rectangle()
            .mapNotNull(::closestPoint)
            .filterNot(pointsWithInfiniteArea::contains)
            .mostFrequent()
            .occurrence

    fun part2(threshold: Int): Int = rectangle()
            .map(::totalDistance)
            .count { it < threshold }
}