package nl.kelpin.fleur.advent2018

import kotlin.math.max
import kotlin.math.min

sealed class Soil {
    abstract fun contains(point: Point): Boolean
}

data class Vertical(val x: Int, val yRange: IntRange) : Soil() {
    companion object {
        private val re = Regex("""x=(\d+), y=(\d+)\.\.(\d+)""")
        fun of(line: String): Vertical? {
            val (x, yFrom, yTo) = re.matchEntire(line)?.destructured ?: return null
            return Vertical(x.toInt(), yFrom.toInt()..yTo.toInt())
        }
    }

    override fun contains(point: Point): Boolean = x == point.x && yRange.contains(point.y)
}

data class Horizontal(val xRange: IntRange, val y: Int) : Soil() {
    companion object {
        private val re = Regex("""y=(\d+), x=(\d+)\.\.(\d+)""")
        fun of(line: String): Horizontal? {
            val (y, xFrom, xTo) = re.matchEntire(line)?.destructured ?: return null
            return Horizontal(xFrom.toInt()..xTo.toInt(), y.toInt())
        }
    }

    override fun contains(point: Point): Boolean = y == point.y && xRange.contains(point.x)
}

class Day17(input: List<String>) {
    val horizontals: List<Horizontal> = input.mapNotNull { Horizontal.of(it) }.sortedBy { it.y }
    private val verticals: List<Vertical> = input.mapNotNull { Vertical.of(it) }.sortedBy { it.x }
    private val clays: Set<Soil> = horizontals.union(verticals)
    private val waters = mutableSetOf<Horizontal>()
    private val flowingWaters = mutableSetOf<Horizontal>()
    private val taps = mutableSetOf(Point(500, 0))
    private val inactiveTaps = mutableSetOf<Point>()
    private val spouts = mutableSetOf<Vertical>()
    private val xRange = horizontals.map { it.xRange.start }.min()!! - 1..horizontals.map { it.xRange.endInclusive }.max()!! + 1
    private val yRange = verticals.map { it.yRange.start }.min()!!..verticals.map { it.yRange.endInclusive }.max()!!

    fun charFor(point: Point): Char = when {
        taps.contains(point) -> '*'
        clays.any { it.contains(point) } -> '#'
        spouts.any { it.contains(point) } -> '|'
        waters.any { it.contains(point) } -> '~'
        flowingWaters.any { it.contains(point) } -> '-'
        else -> ' '
    }

    fun map(): String = yRange.joinToString("\n") { y -> xRange.map { x -> Point(x, y) }.map(::charFor).joinToString("") }

    private fun isSupported(point: Point): Boolean = clays.union(waters).any { it.contains(point.move(Down)) }

    private fun isClay(point: Point): Boolean = clays.any { it.contains(point) }

    // the point where a tap hits a supporting surface
    data class Pok(val point: Point, val leftUnsupported: Int, val leftNoClay: Int, val rightUnsupported: Int, val rightNoClay: Int) {
        fun supportedWater(): Horizontal? {
            if (leftNoClay > leftUnsupported && rightNoClay < rightUnsupported) {
                return Horizontal(leftNoClay + 1 until rightNoClay, point.y)
            }
            return null
        }

        fun flowingWater(): Horizontal = Horizontal(max(leftUnsupported, leftNoClay + 1)..min(rightUnsupported, rightNoClay - 1), point.y)

        fun taps(): List<Point> {
            val result = mutableListOf<Point>()
            if (leftNoClay <= leftUnsupported) {
                result.add(Point(leftUnsupported, point.y))
            }

            if (rightNoClay >= rightUnsupported) {
                result.add(Point(rightUnsupported, point.y))
            }
            return result
        }
    }

    private fun pok(tap: Point): Pok? {
        val pok = (tap.y..yRange.endInclusive)
                .map { tap.copy(y = it) }
                .find(::isSupported) ?: return null
        val toTheLeft = (pok.x downTo xRange.start).map { pok.copy(x = it) }
        val leftUnsupported = toTheLeft.find { !isSupported(it) }!!.x
        val leftNoClay = toTheLeft.find { isClay(it) }?.x ?: xRange.start

        val toTheRight = (pok.x..xRange.endInclusive).map { pok.copy(x = it) }
        val rightUnsupported = toTheRight.find { !isSupported(it) }!!.x
        val rightNoClay = toTheRight.find { isClay(it) }?.x ?: xRange.endInclusive

        return Pok(pok, leftUnsupported, leftNoClay, rightUnsupported, rightNoClay)
    }

    fun next() {
        taps.toList().forEach { tap ->
            val pok = pok(tap)
            if (pok == null) {
                taps.remove(tap)
                inactiveTaps.add(tap)
                return
            }
            val supportedWater = pok.supportedWater()
            if (supportedWater != null) {
                waters.add(supportedWater)
                taps.removeAll { supportedWater.contains(it) }
                inactiveTaps.removeAll { supportedWater.contains(it) }
                // reactivate the inactive taps above
                taps.addAll(inactiveTaps.filter { supportedWater.contains(it.copy(y = supportedWater.y)) })
            } else {
                taps.addAll(pok.taps())
                taps.remove(tap)
                inactiveTaps.add(tap)
            }
        }
    }

    private fun addFlowingWaters() {
        for (tap in inactiveTaps) {
            val pok = pok(tap)
            spouts.add(Vertical(tap.x, tap.y until (pok?.point?.y ?: yRange.endInclusive+1)))
            if (pok != null) {
                flowingWaters.add(pok.flowingWater())
            }
        }
    }

    fun bothParts(): Pair<Int, Int> {
        repeat(10_000) {
            next()
        }
        addFlowingWaters()
        val map = map()
        println(map)
        return map.count { "~*|-".contains(it) } to map.count { it == '~' }
    }
}