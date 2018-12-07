package nl.kelpin.fleur.advent2018

class Day07(val input: List<String>) {
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

    fun nextStep(completedSteps: String): Char? =
            dependencies
                    .filter { !completedSteps.contains(it.key) }
                    .filter { it.value.all { completedSteps.contains(it) } }
                    .map { it.key }
                    .min()

    tailrec fun part1(completed: String = ""): String {
        val next = nextStep(completed)
        return if (next == null) completed else part1(completed + next)
    }
}