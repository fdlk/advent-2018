package nl.kelpin.fleur.advent2018

class Day12(val initialState: String, val rules: Map<String, Char>) {
    companion object {
        fun of(input: List<String>): Day12 = Day12(input[0],
                input.drop(1).map { it.split(" => ") }.map { it[0] to it[1][0] }.toMap())
    }

    fun next(state: String, indexOfFirstPot: Int): Pair<String, Int> {
        val mapped = ("....$state....").windowed(5).map { rules.getOrDefault(it, '.') }
        return mapped.dropWhile { it == '.' }.dropLastWhile { it == '.' }.joinToString("") to
                indexOfFirstPot - 2 + mapped.indexOf('#')
    }

    fun value(state: String, indexOfFirstPot: Long): Long =
            state.toCharArray().foldIndexed(0L) { pot: Int, sum: Long, c: Char ->
        if (c == '#') sum + pot + indexOfFirstPot else sum
    }

    fun part1(): Int {
        var state = initialState
        var index = 0
        repeat(20) {
            val nextGen = next(state, index)
            state = nextGen.first
            index = nextGen.second
        }
        return value(state, index.toLong()).toInt()
    }

    fun part2(): Long {
        var state = initialState
        var previousState: String
        var index = 0
        var iteration = 0
        do {
            val nextGen = next(state, index)
            previousState = state
            state = nextGen.first
            index = nextGen.second
            iteration++
        } while(! state.equals(previousState))
        return value(state, 50000000000 - iteration + index)
    }
}

