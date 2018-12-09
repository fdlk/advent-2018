package nl.kelpin.fleur.advent2018

import java.util.*

class Day09(private val numElves: Int, private val lastMarble: Int) {
    private val scores: MutableMap<Int, Long> = mutableMapOf()
    // marbles.last is the current marble
    private val marbles: Deque<Int> = ArrayDeque(listOf(0))

    private fun <T> Deque<T>.removeMarble(): T {
        cycle(-7)
        return removeLast().also { cycle(1) }
    }

    private fun <T> Deque<T>.insertMarble(marble: T) {
        cycle(1)
        addLast(marble)
    }

    fun highscore(): Long? {
        for (currentMarble in 1..lastMarble) {
            val currentElf = (currentMarble - 1) % numElves + 1
            if (currentMarble % 23 == 0)
                scores[currentElf] = scores.getOrDefault(currentElf, 0) + currentMarble + marbles.removeMarble()
            else
                marbles.insertMarble(currentMarble)
        }
        return scores.values.max()
    }
}