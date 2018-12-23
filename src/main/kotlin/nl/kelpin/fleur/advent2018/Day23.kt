package nl.kelpin.fleur.advent2018

class Day23(input: List<String>) {
    companion object {
        val origin = Point3D(0, 0, 0)
    }

    data class Sphere(val pos: Point3D, val r: Int) {
        companion object {
            val regex = Regex("""pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(\d+)""")
            fun of(input: String): Sphere {
                val (x: kotlin.String, y, z, r) = regex.matchEntire(input)!!.destructured
                return Sphere(Point3D(x.toInt(), y.toInt(), z.toInt()), r.toInt())
            }
        }

        fun contains(point: Point3D) = pos.distanceTo(point) <= r
    }

    val spheres: List<Sphere> = input.map(Sphere.Companion::of)

    fun part1(): Int {
        val largest = spheres.maxBy { it.r }!!
        return spheres.count { it.contains(largest.pos) }
    }

    fun pointsToCheck(n: Int, points: List<Point3D>): List<Point3D> =
            points.map { it.x }.progression(n).flatMap { x ->
                points.map { it.y }.progression(n).flatMap { y ->
                    points.map { it.z }.progression(n).map { z ->
                        Point3D(x, y, z)
                    }
                }
            }

    tailrec fun part2(resolution: Int = 1, center: Point3D? = null): Int {
        val points: List<Point3D>?

        if (center == null)
            points = spheres.map { it.pos }
        else {
            points = listOf(
                    center.plus(Point3D(-1, -1, -1).times(resolution * 15)),
                    center.plus(Point3D(1, 1, 1).times(resolution * 15)))
        }

        val counts = pointsToCheck(resolution, points)
                .associateWith { point -> spheres.count { it.contains(point) } }
        val maxCount = counts.values.max()
        val filteredCounts = counts.filterValues { it == maxCount }
        val closestFound = filteredCounts.minBy { it.key.distanceTo(origin) }!!.key
        if (resolution == 1)
            return closestFound.distanceTo(origin)
        else
            return part2(resolution / 10, closestFound)
    }
}