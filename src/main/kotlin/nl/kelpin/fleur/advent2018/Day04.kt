package nl.kelpin.fleur.advent2018

typealias GuardID = Int
typealias Minute = Int

class Day04(input: List<String>) {
    data class Nap(val guard: GuardID, val minutes: IntRange)

    data class State(val guard: GuardID? = null, val napStarted: Int? = null, val naps: List<Nap> = listOf()) {
        fun beginShift(newGuard: GuardID) = copy(guard = newGuard, napStarted = null)
        fun fallAsleep(minutes: Int) = copy(napStarted = minutes)
        fun wakeUp(minutes: Int) = copy(napStarted = null, naps = naps + Nap(guard!!, napStarted!! until minutes))
    }

    companion object {
        private val beginShiftRE = Regex(""".*Guard #(\d+) begins shift""")
        private val fallAsleepRE = Regex(""".*00:(\d{2})] falls asleep""")
        private val wakesUpRE = Regex(""".*00:(\d{2})] wakes up""")
        private fun MatchResult.intMatch(): Int = destructured.let{ (firstResult) -> firstResult.toInt() }

        fun processLine(status: State, line: String): State = when {
            line.matches(beginShiftRE) -> beginShiftRE.find(line)!!.intMatch().run(status::beginShift)
            line.matches(fallAsleepRE) -> fallAsleepRE.find(line)!!.intMatch().run(status::fallAsleep)
            line.matches(wakesUpRE) -> wakesUpRE.find(line)!!.intMatch().run(status::wakeUp)
            else -> throw IllegalArgumentException("Failed to parse: $line")
        }
    }

    private val guardNaps: Map<GuardID, List<Minute>> = input
            .sorted()
            .fold(State(), ::processLine).naps
            .groupBy { it.guard }
            .mapValues { it.value.flatMap { it.minutes } }

    fun part1(): Int = guardNaps
            .maxBy { it.value.size }!!
            .let { (guardID: GuardID, minutes: List<Minute>) -> guardID * minutes.mostFrequent().element }

    fun part2(): Int = guardNaps
            .mapValues { it.value.mostFrequent() }
            .maxBy { it.value.occurrence }!!
            .let { (guardID: GuardID, mostFrequent: Frequency<Minute>) -> guardID * mostFrequent.element }
}