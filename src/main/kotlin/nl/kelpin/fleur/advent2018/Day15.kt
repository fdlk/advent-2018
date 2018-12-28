package nl.kelpin.fleur.advent2018

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
    val pointComparison = compareBy<Point>({ it.y }, { it.x })

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

    fun move(critter: Critter, targets: Set<Point>, occupied: Set<Point>): Point {
        if (targets.any { critter.location.isInRange(it) }) return critter.location
        val closed: MutableSet<Point> = (occupied + critter.location).toMutableSet()
        // maps point reached to the first step taken to get there
        var current: Map<Point, Point> = neighbors(critter.location).filter { !closed.contains(it) }.map { it to it }.toMap()
        while (!current.isEmpty()) {
            val goalReached: Point? = current.keys
                    .filter { targets.any { target -> target.isInRange(it) } }
                    .minWith(pointComparison)
            if (goalReached != null) return current[goalReached]!!
            closed.addAll(current.keys)
            val nextSteps = current.flatMap { (location, firstStep) ->
                neighbors(location)
                        .filter { !closed.contains(it) }
                        .map { it to firstStep }
            }
            // pick preferred first step when merging the options into a map
            current = nextSteps
                    .groupBy { it.first }
                    .mapValues { it.value.map { it.second }.minWith(pointComparison)!! }
        }
        return critter.location
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
                todo.update(target, molestedTarget)
                done.update(target, molestedTarget)
            }
            done.add(movedCritter)
        }
        return incompleteRound to done.toSet()
    }

    private fun simulate(initialCritters: Set<Critter>): Pair<Int, Set<Critter>> {
        var critters = initialCritters
        var i = 0
        while (critters.map { it.type }.distinct().size == 2) {
            val (incompleteRound, nextCritters) = nextRound(critters)
            critters = nextCritters
            if (!incompleteRound) i++
        }
        return Pair(i, critters)
    }

    fun part1(): Int {
        val (i, critters) = simulate(initialCritters)
        return i * critters.sumBy { it.hitPoints }
    }

    fun part2(): Int {
        var attackPower = 4
        val numElves = initialCritters.count { it.type == 'E' }
        while (true) {
            val (i, critters) = simulate(initialCritters
                    .map { if (it.type == 'E') it.copy(attackPower = attackPower) else it }
                    .toSet())
            if (critters.size == numElves && critters.all { it.type == 'E' }) {
                return i * critters.sumBy { it.hitPoints }
            }
            attackPower++
        }
    }
}