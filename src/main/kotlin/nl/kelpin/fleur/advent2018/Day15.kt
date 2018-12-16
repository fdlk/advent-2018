package nl.kelpin.fleur.advent2018

import java.util.*

class Day15(val input: List<String>) {
    val cave: List<String> = input.map {
        it.map {
            when (it) {
                '#' -> '#'
                else -> '.'
            }
        }.joinToString("")
    }
    val xRange: IntRange = 0 until cave[0].length
    val yRange: IntRange = 0 until cave.size
    val initialCritters = input.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            when (c) {
                'E', 'G' -> Critter(Point(x, y), c)
                else -> null
            }
        }
    }.flatten().filterNotNull().toSet()
    val critterComparison = compareBy<Critter>({ it.location.y }, { it.location.x })
    val targetComparison = compareBy<Critter>({ it.hitPoints }, { it.location.y }, { it.location.x })

    fun isOpenCave(p: Point): Boolean = with(p) { xRange.contains(x) && yRange.contains(y) && cave[y][x] == '.' }
    fun neighbors(p: Point): List<Point> = listOf(Up, Left, Right, Down).map { p.move(it) }.filter(::isOpenCave)

    data class Critter(val location: Point, val type: Char, val hitPoints: Int = 200, val attackPower: Int = 3) {
        fun attackedBy(other: Critter): Critter? {
            val leftoverHitPoints = this.hitPoints - other.attackPower
            return if (leftoverHitPoints > 0) copy(hitPoints = leftoverHitPoints) else null
        }
    }

    fun mapWith(critters: Set<Critter>): String = xRange.map { y ->
        yRange.map { x -> Point(x, y) }.map { point ->
            when {
                !isOpenCave(point) -> '#'
                else -> critters.find { it.location == point }?.type ?: '.'
            }
        }.joinToString("")
    }.joinToString("\n")

    fun Point.isInRange(other: Point): Boolean = distanceTo(other) == 1

    tailrec fun reconstruct(comeFrom: Map<Point, Point>, current: Point, start: Point): Point = when {
        (current == start) -> start
        (comeFrom[current] == start) -> current
        else -> reconstruct(comeFrom, comeFrom[current]!!, start)
    }

    fun move(critter: Critter, targets: Set<Point>, occupied: Set<Point>): Point {
        val open: Deque<Point> = ArrayDeque()
        val closed: MutableList<Point> = mutableListOf()
        var current: Point = critter.location
        // Store the first step to take from critter's current location
        val comeFrom: MutableMap<Point, Point> = mutableMapOf()
        do {
            if (!open.isEmpty()) {
                current = open.poll()
            }
            if (targets.any { current.isInRange(it) }) return current
            val next = neighbors(current)
                    .filter { !occupied.contains(it) }
                    .filter { !closed.contains(it) }
                    .filter { !open.contains(it) }
            comeFrom.putAll(next.associateWith { current })
            val done = next.find { reached -> targets.any { reached.isInRange(it) } }
            if (done != null) {
                // reconstruct path
                val result = reconstruct(comeFrom, done, critter.location)
                return result
            }
            next.forEach { open.offerLast(it) }
            closed.add(current)
        } while (!open.isEmpty())
        return critter.location
    }

    fun MutableList<Critter>.replaceIfPresent(target: Critter, molestedTarget: Critter?) {
        val targetIndex = indexOf(target)
        if (targetIndex >= 0) {
            if (molestedTarget == null) {
                removeAt(targetIndex)
            } else {
                this[targetIndex] = molestedTarget
            }
        }
    }

    fun nextRound(critters: Set<Critter>): Pair<Boolean, Set<Critter>> {
        val todo = critters.sortedWith(critterComparison).toMutableList()
        val done = mutableListOf<Critter>()
        var incompleteRound = false
        while (todo.isNotEmpty()) {
            val critter: Critter = todo.removeAt(0)
            val others = (todo + done)
            val enemies = others.filter { critter.type != it.type }
            if (enemies.isEmpty()) {
                incompleteRound = true
            }
            val movedCritter = critter.copy(location = move(critter, enemies.map { it.location }.toSet(), others.map { it.location }.toSet()))
            val target = enemies.filter { it.location.isInRange(movedCritter.location) }.sortedWith(targetComparison).firstOrNull()
            if (target != null) {
                val molestedTarget = target.attackedBy(movedCritter)
                todo.replaceIfPresent(target, molestedTarget)
                done.replaceIfPresent(target, molestedTarget)
            }
            done.add(movedCritter)
        }
        return incompleteRound to done.toSet()
    }

    fun part1(): Int {
        var critters = initialCritters
        var i = 0
        while (critters.map { it.type }.distinct().size == 2) {
            val next = nextRound(critters)
            critters = next.second
            if (!next.first) i++
        }
        return i * critters.sumBy { it.hitPoints }
    }
}