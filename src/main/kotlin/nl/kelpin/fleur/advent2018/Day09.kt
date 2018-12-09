package nl.kelpin.fleur.advent2018

import java.util.*

class Day09(val numElves: Int, val lastMarble: Int) {
    val marbles: LinkedList<Int> = LinkedList(listOf(0))
    var iterator: MutableListIterator<Int> = marbles.listIterator()
    val scores: MutableMap<Int, Long> = mutableMapOf<Int, Long>()
    var marble: Int = 0

    fun circularNext(): Int {
        if (!iterator.hasNext()) {
            iterator = marbles.listIterator()
        }
        return iterator.next()
    }

    fun circularPrevious(): Int {
        if (!iterator.hasPrevious()) {
            iterator = marbles.listIterator(marbles.size)
        }
        return iterator.previous()
    }

    fun highscore(): Long? {
        while (++marble <= lastMarble) {
            val currentElf = (marble - 1) % numElves + 1
            if (marble % 23 == 0) {
                val removed = (1..8).reduce { _, _ -> circularPrevious() }
                iterator.remove()
                scores[currentElf] = scores.getOrDefault(currentElf, 0) + marble + removed
            } else {
                circularNext()
                circularNext()
                iterator.add(marble)
                circularPrevious()
            }
        }
        return scores.map { it.value }.max()
    }
}