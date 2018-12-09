package nl.kelpin.fleur.advent2018

import java.util.*

class Day09(val numElves: Int, val lastMarble: Int) {
    class Marbles(initial: List<Int> = listOf(0)) {
        private val marbles: LinkedList<Int> = LinkedList(initial)
        // iterator.previous() is the current marble.
        private var iterator: MutableListIterator<Int> = marbles.listIterator()

        fun circularNext(): Int {
            if (!iterator.hasNext()) iterator = marbles.listIterator()
            return iterator.next()
        }

        fun circularPrevious(): Int {
            if (!iterator.hasPrevious()) iterator = marbles.listIterator(marbles.size)
            return iterator.previous()
        }

        fun remove(): Int {
            repeat(7) { circularPrevious() }
            val removed = circularPrevious()
            iterator.remove()
            circularNext()
            return removed
        }

        fun insert(marble: Int) {
            circularNext()
            iterator.add(marble)
        }

        override fun toString(): String = marbles
                .mapIndexed { index, i -> if (index == iterator.previousIndex()) "(${i})" else " ${i} " }
                .joinToString("")
    }

    val scores: MutableMap<Int, Long> = mutableMapOf()
    val marbles: Marbles = Marbles()

    fun highscore(): Long? {
        for (currentMarble in 1..lastMarble) {
            val currentElf = (currentMarble - 1) % numElves + 1
            if (currentMarble % 23 == 0) {
                scores[currentElf] = scores.getOrDefault(currentElf, 0) + currentMarble + marbles.remove()
            } else {
                marbles.insert(currentMarble)
            }
//            println("[${currentElf}] ${marbles}")
        }
        return scores.values.max()
    }
}