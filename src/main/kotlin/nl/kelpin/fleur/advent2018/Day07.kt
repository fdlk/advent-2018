package nl.kelpin.fleur.advent2018

class Day07(val input: List<String>, val baseTimeToCompletion: Int = 60, val workers: Int = 5) {
    companion object {
        private val dependencyRE = Regex("""Step (\w) must be finished before step (\w) can begin\.""")
    }

    fun parse(): Map<Char, Set<Char>> {
        val dependencies: Map<Char, Set<Char>> = input
                .map(dependencyRE::matchEntire)
                .filterNotNull()
                .map { it.groupValues }
                .map { Pair(it[2][0], it[1][0]) }
                .groupBy { it.first }
                .mapValues { it.value.fold(setOf<Char>(), { acc, dependency -> acc + dependency.second }) }
        val steps = dependencies.values.flatMap { it }.union(dependencies.keys)
        return steps.map { Pair(it, dependencies[it] ?: emptySet()) }.toMap()
    }

    val dependencies: Map<Char, Set<Char>> = parse()

    fun nextStep(completed: String, current: List<Char> = emptyList()): Char? =
            dependencies
                    .filter { !completed.contains(it.key) }
                    .filter { !current.contains(it.key) }
                    .filter { it.value.all { completed.contains(it) } }
                    .map { it.key }
                    .min()

    tailrec fun part1(completed: String = ""): String {
        val next = nextStep(completed)
        return if (next == null) completed else part1(completed + next)
    }

    data class Assignment(val step: Char, val started: Int)

    fun Assignment.done(): Int = started + (step - 'A') + baseTimeToCompletion + 1

    data class State(val completed: String = "", val currentTime: Int = 0, val assignments: Set<Assignment> = emptySet())

    fun State.nextStep(): Char? = nextStep(completed, assignments.map { it.step })
    fun State.done(): Boolean = assignments.isEmpty() && nextStep() == null
    fun State.assignWorker(): State? {
        val next = nextStep()
        return if (assignments.size < workers && next != null) {
            copy(assignments = assignments + Assignment(next, currentTime))
        } else null
    }

    fun State.wait(): State {
        val firstDone = assignments.minBy { it.done() }!!
        return copy(assignments = assignments - firstDone,
                currentTime = firstDone.done(),
                completed = completed + firstDone.step)
    }

    tailrec fun part2(state: State = State()): Int =
            if (state.done()) state.currentTime
            else part2(state.assignWorker() ?: state.wait())
}