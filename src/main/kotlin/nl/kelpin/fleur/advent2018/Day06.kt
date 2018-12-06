package nl.kelpin.fleur.advent2018

class Day06(input: List<String>) {
    fun List<Int>.range(): IntRange = min()!! - 1..max()!! + 1

    val points: List<Point> = input.map { Point(it.split(",").map { it.trim().toInt() }) }
    val xRange: IntRange = points.map { it.x }.range()
    val yRange: IntRange = points.map { it.y }.range()

    fun closestPoint(p: Point): Point? {
        val (a, b) = points.sortedBy { it.distanceTo(p) }.take(2)
        return if (a.distanceTo(p) == b.distanceTo(p)) null else a
    }

    fun edgePoints(): Set<Point> {
        val bottomAndTop = xRange.flatMap { x ->
            setOf(
                    closestPoint(Point(x, yRange.start)),
                    closestPoint(Point(x, yRange.endInclusive))
            )
        }.filterNotNull().toSet()
        val leftAndRight = yRange.flatMap { y ->
            setOf(
                    closestPoint(Point(xRange.start, y)),
                    closestPoint(Point(xRange.endInclusive, y))
            )
        }.filterNotNull().toSet()
        return bottomAndTop.union(leftAndRight)
    }


    fun part1(): Int? {
        val edgePoints:Set<Point> = edgePoints()
        return xRange.flatMap { x -> yRange.map { y -> closestPoint(Point(x, y)) } }
                .filterNotNull()
                .filter{!edgePoints.contains(it)}
                .groupingBy { it }
                .eachCount()
                .values
                .max()
    }
}