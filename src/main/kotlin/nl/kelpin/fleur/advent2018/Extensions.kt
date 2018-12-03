package nl.kelpin.fleur.advent2018

fun String.matchingChars(other: String): String =
    this.zip(other).filter { it.first == it.second }
        .map { it.first }
        .joinToString("")

fun IntRange.overlaps(other: IntRange): Boolean =
    start <= other.endInclusive && other.start <= endInclusive