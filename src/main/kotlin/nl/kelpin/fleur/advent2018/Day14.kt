package nl.kelpin.fleur.advent2018

class Day14 {
    private val scores = sequence {
        var elf1 = 0
        var elf2 = 1
        val scores: MutableList<Byte> = mutableListOf(3, 7)
        yieldAll(scores)
        while (true) {
            val digits: List<Byte> = (scores[elf1] + scores[elf2]).toString().map(Char::asDigit)
            scores.addAll(digits)
            yieldAll(digits)
            elf1 = (elf1 + 1 + scores[elf1]) % scores.size
            elf2 = (elf2 + 1 + scores[elf2]) % scores.size
        }
    }

    fun part1(n: Int): String = scores.drop(n).take(10).joinToString("")

    fun part2(input: String): Int = scores.windowed(input.length).indexOf(input.toCharArray().map(Char::asDigit))
}