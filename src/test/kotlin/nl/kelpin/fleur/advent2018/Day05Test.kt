package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Day05Test {
    @Test
    fun `Replace aBbA`() {
        assertThat(Day05.replaced("aBbA")).isEqualTo("")
    }

    @Test
    fun `Replace aBbBA`() {
        assertThat(Day05.replaced("aBbBA")).isEqualTo("aBA")
    }

    @Test
    fun `Replace aBbBbA`() {
        assertThat(Day05.replaced("aBbBbA")).isEqualTo("")
    }

    @Test
    fun `Replace aBbhkK`() {
        assertThat(Day05.replaced("aBbhkK")).isEqualTo("ah")
    }

    @Tag("Solution")
    @Test()
    fun `Part 1 solution`() {
        assertThat(Day05(resourceAsList("day05.txt")[0]).part1()).isEqualTo(9348)
    }

    @Test
    fun `Part 2 works for sample data`() {
        assertThat(Day05("dabAcCaCBAcCcaDA").part2()).isEqualTo(4)
    }

    @Tag("Solution")
    @Test()
    fun `Part 2 solution`() {
        assertThat(Day05(resourceAsList("day05.txt")[0]).part2()).isEqualTo(4996)
    }
}