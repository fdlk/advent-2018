package nl.kelpin.fleur.advent2018

class Day14 {
    var elf1 = 0
    var elf2 = 1
    val scores = mutableListOf(3, 7)

    fun round() {
        val total = scores[elf1] + scores[elf2]
        val digit1 = total / 10
        val digit2 = total % 10
        if (digit1 > 0) scores.add(digit1)
        scores.add(digit2)
        elf1 = (elf1 + 1 + scores[elf1]) % scores.size
        elf2 = (elf2 + 1 + scores[elf2]) % scores.size
    }

    fun part1(n: Int): String {
        while (scores.size < n + 10) {
            round()
        }
        return scores.subList(n, n + 10).map { '0' + it }.joinToString("")
    }

    fun part2(input: String): Int? {
        val subList: List<Int> = input.toCharArray().map {it - '0'}
        while (true) {
            repeat(1_000_000) { round() }
            val result = scores.windowed(subList.size).indexOf(subList)
            if (result >= 0) {
                return result
            }
        }
    }
}