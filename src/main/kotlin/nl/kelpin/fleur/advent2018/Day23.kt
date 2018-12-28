package nl.kelpin.fleur.advent2018

import arrow.syntax.function.memoize
import java.util.*

class Day23(input: List<String>) {
    companion object {
        val origin = Point3D(0, 0, 0)
    }

    data class Sphere(val pos: Point3D, val r: Int) {
        companion object {
            private val regex = Regex("""pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(\d+)""")
            fun of(input: String): Sphere {
                val (x, y, z, r) = regex.matchEntire(input)!!.destructured
                return Sphere(Point3D(x.toInt(), y.toInt(), z.toInt()), r.toInt())
            }
        }

        fun contains(point: Point3D) = pos.distanceTo(point) <= r
    }

    private val spheres: List<Sphere> = input.map(Sphere.Companion::of)

    fun part1(): Int {
        val largest = spheres.maxBy { it.r }!!
        return spheres.count { largest.contains(it.pos) }
    }

    data class Box(val xs: IntRange, val ys: IntRange, val zs: IntRange) {
        fun distanceFrom(point: Point3D): Int = with(point) { xs.distance(x) + ys.distance(y) + zs.distance(z) }

        fun contains(point: Point3D): Boolean = distanceFrom(point) == 0

        fun overlaps(sphere: Sphere): Boolean = distanceFrom(sphere.pos) <= sphere.r

        fun dimension(): Point3D = Point3D(xs.length(), ys.length(), zs.length())

        fun split(): Set<Box> = xs.split().flatMap { newXs ->
            ys.split().flatMap { newYs ->
                zs.split().map { newZs -> Box(newXs, newYs, newZs) }
            }
        }.toSet()
    }

    val overlappingSpheres = { box: Box -> spheres.count(box::overlaps) }.memoize()

    val initialBox = Box(spheres.map { it.pos.x }.range(),
            spheres.map { it.pos.y }.range(),
            spheres.map { it.pos.z }.range())

    fun part2(): Int? {
        val queue: PriorityQueue<Box> = PriorityQueue(
                compareByDescending(overlappingSpheres).then(compareBy { it.distanceFrom(origin) }))
        queue.add(initialBox)
        while (!queue.isEmpty()) {
            val box = queue.poll()
            println("overlaps = ${overlappingSpheres(box)}, size = ${box.dimension()}")
            if (box.dimension() == Point3D(1, 1, 1)) {
                return box.distanceFrom(origin)
            }
            queue.addAll(box.split())
        }
        return null
    }
}