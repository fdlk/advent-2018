package nl.kelpin.fleur.advent2018

class Day01(input: List<String>) {
    private val deltas = input.map(String::toInt)
    fun part1(): Int = deltas.sum()

    private val frequencies = sequence {
        var currentFrequency = 0
        while (true) {
            for (delta in deltas) {
                currentFrequency += delta
                yield(currentFrequency)
            }
        }
    }

    fun part2(): Int {
        val encounteredFrequencies = mutableSetOf<Int>()
        return frequencies.find { !encounteredFrequencies.add(it) }!!
    }
}
