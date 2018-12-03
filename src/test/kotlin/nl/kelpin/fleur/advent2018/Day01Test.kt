package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

class Day01Test {
    @Test()
    fun `Part 1 matches example`() {
        assertThat(Day01(listOf("+1", "+1", "+1")).part1()).isEqualTo(3)
        assertThat(Day01(listOf("+1", "+1", "-2")).part1()).isEqualTo(0)
        assertThat(Day01(listOf("-1", "-2", "-3")).part1()).isEqualTo(-6)
    }

    @Tag("Solution")
    @Test()
    fun `Part 1 solution`() {
        assertThat(Day01(resourceAsList("day01.txt")).part1()).isEqualTo(402)
    }

    @Tag("Solution")
    @Test()
    fun `Part 2 solution`() {
        assertThat(Day01(resourceAsList("day01.txt")).part2()).isEqualTo(481)
    }
}