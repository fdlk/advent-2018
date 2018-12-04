package nl.kelpin.fleur.advent2018

class Day04(val input: List<String>) {

    data class Nap(val guard: Int, val minutes: IntRange)
    data class Status(val guard: Int? = null, val napStarted: Int? = null, val naps: List<Nap> = listOf()) {
        fun beginShift(newGuard: Int) = copy(guard = newGuard, napStarted = null)
        fun fallAsleep(minutes: Int) = copy(napStarted = minutes)
        fun wakeUp(minutes: Int) = copy(napStarted = null, naps = naps + Nap(guard!!, napStarted!! until minutes))
    }

    companion object {
        val beginShiftRE = Regex(""".*Guard #(\d+) begins shift""")
        val fallAsleepRE = Regex(""".*00:(\d{2})] falls asleep""")
        val wakesUpRE = Regex(""".*00:(\d{2})] wakes up""")

        fun processLine(status: Status, line: String): Status = when {
            line.matches(beginShiftRE) -> beginShiftRE.find(line)!!.destructured.let { (guardId) -> status.beginShift(guardId.toInt()) }
            line.matches(fallAsleepRE) -> fallAsleepRE.find(line)!!.destructured.let { (minutes) -> status.fallAsleep(minutes.toInt()) }
            line.matches(wakesUpRE) -> wakesUpRE.find(line)!!.destructured.let { (minutes) -> status.wakeUp(minutes.toInt()) }
            else -> throw IllegalArgumentException("Failed to parse: $line")
        }
    }

    val naps = input.sorted().fold(Status(), ::processLine).naps.groupBy { it.guard }

    fun part1(): Int {
        val guard = naps
                .maxBy { it.value.map { it.minutes.length() }.sum() }!!
                .key
        val minute = naps[guard]!!
                .flatMap { it.minutes.toList() }
                .groupingBy { it }
                .eachCount()
                .maxBy { it.value }!!
                .key
        return guard * minute
    }

    fun part2(): Int {
        val guardWhoSleptMost = naps
                .mapValues {
                    it.value.flatMap { it.minutes.toList() }
                            .groupingBy { it }
                            .eachCount()
                            .maxBy { it.value }

                }.maxBy { it.value!!.value }!!
        val guard = guardWhoSleptMost.key
        val minuteSleptMost = guardWhoSleptMost.value!!.key
        return guard * minuteSleptMost
    }
}