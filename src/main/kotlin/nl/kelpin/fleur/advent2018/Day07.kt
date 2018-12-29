package nl.kelpin.fleur.advent2018

import java.util.*

class Day07(val input: List<String>, val baseTimeToCompletion: Int = 60, val workers: Int = 5) {
    companion object {
        private val dependencyRE = Regex("""Step (\w) must be finished before step (\w) can begin\.""")
    }

    private fun parse(): Map<Char, Set<Char>> {
        val dependencies: Map<Char, Set<Char>> = input
                .mapNotNull(dependencyRE::matchEntire)
                .map { it.destructured.let { (a, b) -> b[0] to a[0] } }
                .groupBy { it.first }
                .mapValues { it.value.fold(setOf<Char>()) { acc, dependency -> acc + dependency.second } }
        val steps = dependencies.values.flatten().union(dependencies.keys)
        return steps
                .map { it to (dependencies[it] ?: emptySet()) }
                .toMap()
    }

    val dependencies: Map<Char, Set<Char>> = parse()

    fun nextStep(completed: String, current: List<Char> = emptyList()): Char? =
            dependencies
                    .filterKeys { !completed.contains(it) }
                    .filterKeys { !current.contains(it) }
                    .filterValues { it.all { dependency -> completed.contains(dependency) } }
                    .keys
                    .min()

    tailrec fun part1(completed: String = ""): String {
        val next = nextStep(completed) ?: return completed
        return part1(completed + next)
    }

    data class Assignment(val step: Char, val started: Int)

    private fun Assignment.done(): Int = started + (step - 'A') + baseTimeToCompletion + 1

    data class State(val completed: String = "", val currentTime: Int = 0, val assignments: Set<Assignment> = emptySet())

    private fun State.nextStep(): Char? = nextStep(completed, assignments.map(Assignment::step))
    private fun State.done(): Boolean = assignments.isEmpty() && nextStep() == null
    private fun State.assignWorker(): State? = Optional.ofNullable(nextStep())
            .filter { assignments.size < workers }
            .map { Assignment(it, currentTime) }
            .map { copy(assignments = assignments + it) }
            .orElse(null)

    private fun State.wait(): State {
        val firstDone = assignments.minBy { it.done() }!!
        return copy(assignments = assignments - firstDone,
                currentTime = firstDone.done(),
                completed = completed + firstDone.step)
    }

    tailrec fun part2(state: State = State()): Int =
            if (state.done()) state.currentTime
            else part2(state.assignWorker() ?: state.wait())
}