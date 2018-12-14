package nl.kelpin.fleur.advent2018

class Day14 {
    val scores = sequence {
        var elf1 = 0
        var elf2 = 1
        val scores = mutableListOf(3, 7)
        yieldAll(scores)
        while (true) {
            val total = scores[elf1] + scores[elf2]
            val digit1 = total / 10
            val digit2 = total % 10
            if (digit1 > 0) {
                scores.add(digit1)
                yield(digit1)
            }
            scores.add(digit2)
            yield(digit2)
            elf1 = (elf1 + 1 + scores[elf1]) % scores.size
            elf2 = (elf2 + 1 + scores[elf2]) % scores.size
        }
    }

    fun part1(n: Int): String = scores.drop(n).take(10).map { '0' + it }.joinToString("")

    fun part2(input: String): Int = scores.windowed(input.length).indexOf(input.toCharArray().map { it - '0' })
}