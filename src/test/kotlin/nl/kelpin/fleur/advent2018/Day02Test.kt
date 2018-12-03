package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

class Day02Test {

    @Test()
    fun `Part 1 matches example`() {
        Assertions.assertThat(Day02(listOf("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab")).part1()).isEqualTo(12)
    }

    @Tags(Tag("Solution"))
    @Test()
    fun `Part 1 solution`() {
        println(Day02(resourceAsList("day02.txt")).part1())
    }

    @Test()
    fun `Finds matching chars in two strings`() {
        Assertions.assertThat("fghij".matchingChars("fguij")).isEqualTo("fgij")
        Assertions.assertThat("abcde".matchingChars("axcye")).isEqualTo("ace")
    }

    @Tags(Tag("Solution"))
    @Test()
    fun `Part 2 solution`() {
        println(Day02(resourceAsList("day02.txt")).part2())
    }

}