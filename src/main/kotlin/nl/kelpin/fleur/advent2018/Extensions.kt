package nl.kelpin.fleur.advent2018

fun String.matchingChars(other: String): String =
    this.zip(other).filter { it.first == it.second }
        .map { it.first }
        .joinToString("")