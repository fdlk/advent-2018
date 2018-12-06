package nl.kelpin.fleur.advent2018

class Day05(input: String) {
    companion object {
        private fun isPair(a: Char, b: Char): Boolean = Math.abs(a - b) == 32
        private fun isStartOfPair(line: String, i: Int) = i < line.length - 1 && isPair(line[i], line[i + 1])
        tailrec fun replaced(line: String): String {
            val index = line.indices.find { isStartOfPair(line, it) }
            return if (index == null) line else replaced(line.filterIndexed { i, _ -> i !in (index..index + 1) })
        }
    }

    private val replacedInput = replaced(input)

    fun part1(): Int = replacedInput.length

    fun part2(): Int? = replacedInput
            .toLowerCase()
            .toSet()
            .map { filteredChar -> replacedInput.filter { it.toLowerCase() != filteredChar } }
            .map { replaced(it).length }
            .min()
}