package nl.kelpin.fleur.advent2018

import java.util.*

fun String.matchingChars(other: String): String =
    this.zip(other).filter { it.first == it.second }
        .map { it.first }
        .joinToString("")

fun IntRange.overlaps(other: IntRange): Boolean =
    start <= other.endInclusive && other.start <= endInclusive

fun IntRange.length(): Int = endInclusive - start

// Frequency counting
data class Frequency<T>(val element: T, val occurrence: Int)
fun <T> Collection<T>.mostFrequent(): Frequency<T> {
    val (value: T, count: Int) = groupingBy { it }.eachCount().maxBy { it.value }!!
    return Frequency(value, count)
}

fun List<Int>.range(): IntRange = min()!!..max()!!

fun <T> Deque<T>.cycle(n: Int) {
    repeat(n) { addLast(removeFirst()) }
    repeat(-n) { addFirst(removeLast()) }
}