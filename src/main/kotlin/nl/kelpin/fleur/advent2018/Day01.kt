package nl.kelpin.fleur.advent2018

class Day01(input: List<String>) {
    private val deltas = input.map(String::toInt)

    fun part1(): Int = deltas.sum()

    fun part2(): Int {
        val encounteredFrequencies = mutableSetOf<Int>()
        var currentFrequency = 0
        while (true) {
            for (delta in deltas) {
                val alreadyEncountered = !encounteredFrequencies.add(currentFrequency)
                if (alreadyEncountered) return currentFrequency
                currentFrequency += delta
            }
        }
    }
}
