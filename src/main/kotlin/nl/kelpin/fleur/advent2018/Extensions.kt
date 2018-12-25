package nl.kelpin.fleur.advent2018

import java.util.*

fun String.matchingChars(other: String): String =
        this.zip(other).filter { it.first == it.second }
                .map { it.first }
                .joinToString("")

fun IntRange.overlaps(other: IntRange): Boolean =
        start <= other.endInclusive && other.start <= endInclusive

fun IntRange.length(): Int = endInclusive - start + 1

// Frequency counting
data class Frequency<T>(val element: T, val occurrence: Int)

fun <T> Collection<T>.mostFrequent(): Frequency<T> {
    val (value: T, count: Int) = groupingBy { it }.eachCount().maxBy { it.value }!!
    return Frequency(value, count)
}

fun List<Int>.range(): IntRange = min()!!..max()!!
fun List<Int>.progression(n: Int): IntProgression = min()!!..max()!! step n

fun <T> Deque<T>.cycle(n: Int) {
    repeat(n) { addLast(removeFirst()) }
    repeat(-n) { addFirst(removeLast()) }
}

fun <T> MutableList<T>.update(from: T, to: T?) {
    if (to == null) {
        remove(from)
    } else {
        val index = indexOf(from)
        if (index != -1) {
            this[index] = to
        }
    }
}

fun Char.asDigit() = if (isDigit()) (this - '0').toByte() else throw IllegalArgumentException()