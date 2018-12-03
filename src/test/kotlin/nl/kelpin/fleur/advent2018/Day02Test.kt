package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Day02Test {

    @Test()
    fun `Part 1 matches example`() {
        assertThat(Day02(listOf("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab")).part1()).isEqualTo(12)
    }

    @Tag("Solution")
    @Test()
    fun `Part 1 solution`() {
        assertThat(Day02(resourceAsList("day02.txt")).part1()).isEqualTo(4940)
    }

    @Test()
    fun `Finds matching chars in two strings`() {
        assertThat("fghij".matchingChars("fguij")).isEqualTo("fgij")
        assertThat("abcde".matchingChars("axcye")).isEqualTo("ace")
    }

    @Tag("Solution")
    @Test()
    fun `Part 2 solution`() {
        assertThat(Day02(resourceAsList("day02.txt")).part2()).isEqualTo("wrziyfdmlumeqvaatbiosngkc")
    }

}