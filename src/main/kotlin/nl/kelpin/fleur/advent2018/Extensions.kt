package nl.kelpin.fleur.advent2018

import java.util.*

fun String.matchingChars(other: String): String =
        this.zip(other).filter { it.first == it.second }
                .map { it.first }
                .joinToString("")

fun IntRange.overlaps(other: IntRange): Boolean =
        start <= other.endInclusive && other.start <= endInclusive

fun IntRange.length(): Int = endInclusive - start + 1

fun IntRange.split(): Set<IntRange> =
        if (endInclusive == start)
            setOf(this)
        else {
            val halfWay = start + (endInclusive - start) / 2
            setOf(start .. halfWay, halfWay+1..endInclusive)
        }

fun IntRange.distance(x: Int) = when{
    contains(x) -> 0
    x < start -> start - x
    else -> x - endInclusive
}


// Frequency counting
data class Frequency<T>(val element: T, val occurrence: Int)

fun <T> Collection<T>.mostFrequent(): Frequency<T> {
    val (value: T, count: Int) = groupingBy { it }.eachCount().maxBy { it.value }!!
    return Frequency(value, count)
}

fun List<Int>.range(): IntRange = min()!!..max()!!
fun IntRange.stretch(factor: Float): IntRange {
    val diff = ((endInclusive - start) * factor).toInt()
    return (start - diff) .. (endInclusive + diff)
}

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

fun <T> Set<T>.split(predicate: (T) -> Boolean): Pair<Set<T>, Set<T>> {
    val filtered = this.filter(predicate).toSet()
    return filtered to this - filtered
}

fun Char.asDigit() = if (isDigit()) (this - '0').toByte() else throw IllegalArgumentException()