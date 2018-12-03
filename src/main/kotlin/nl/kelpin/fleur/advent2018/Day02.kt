package nl.kelpin.fleur.advent2018

class Day02(private val input: List<String>) {
    fun part1(): Int {
        val counts = input.map { it.groupingBy { it }.eachCount() }
        val twos = counts.count { it.values.contains(2) }
        val threes = counts.count { it.values.contains(3) }
        return twos * threes
    }

    fun part2(): String? = input.flatMap { a ->
        input.map { b -> b.matchingChars(a) }
                .filter { it.length == a.length - 1 }
    }.first()
}
