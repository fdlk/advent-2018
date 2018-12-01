package nl.kelpin.fleur.advent2018

class Day01(input: List<String>) {
    private val ints = input.map(String::toInt)

    fun part1(): Int = ints.sum()

    tailrec fun part2(encountered: MutableSet<Int> = mutableSetOf(0), frequency: Int = 0, index: Int = 0): Int {
        val newFrequency: Int = frequency + ints[index]
        return if (!encountered.add(newFrequency))
            newFrequency
        else
            part2(encountered, newFrequency, (index + 1).rem(ints.size))
    }
}
