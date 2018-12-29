package nl.kelpin.fleur.advent2018

class Day12(val initialState: State, val rules: Map<String, Char>) {
    companion object {
        fun of(input: List<String>): Day12 = Day12(State(input[0], 0),
                input.drop(1).map { it.split(" => ") }.map { it[0] to it[1][0] }.toMap())
    }

    data class State(val pots: String, val indexOfFirstPot: Long) {
        fun value(): Long =
                pots.toCharArray().foldIndexed(0L) { pot: Int, sum: Long, c: Char ->
                    if (c != '#') sum else sum + pot + indexOfFirstPot
                }
    }

    fun State.next(): State {
        val mapped = ("....$pots....").windowed(5).map { rules.getOrDefault(it, '.') }
        return State(mapped.dropWhile { it == '.' }
                .dropLastWhile { it == '.' }
                .joinToString(""), indexOfFirstPot - 2 + mapped.indexOf('#'))
    }


    fun part1(): Long {
        var state = initialState
        repeat(20){
            state = state.next()
        }
        return state.value()
    }

    fun part2(): Long {
        var state = initialState
        var previousState: State
        var iteration = 0
        do {
            previousState = state
            state = state.next()
            iteration++
        } while (state.pots != previousState.pots)
        return state.copy(indexOfFirstPot = 50000000000 - iteration + state.indexOfFirstPot).value()
    }
}

