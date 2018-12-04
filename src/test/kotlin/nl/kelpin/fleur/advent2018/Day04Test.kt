package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Day04Test {
    @Test
    fun `Parse start of shift`() {
        val line = "[1518-02-10 23:47] Guard #631 begins shift"
        assertThat(Day04.processLine(Day04.Status(), line)).isEqualTo(Day04.Status().beginShift(631))
    }

    @Test
    fun `Part 1 solution works for sample data`() {
        assertThat(Day04(resourceAsList("day04-sample.txt")).part1()).isEqualTo(240)
    }

    @Tag("Solution")
    @Test()
    fun `Part 1 solution`() {
        assertThat(Day04(resourceAsList("day04.txt")).part1()).isEqualTo(30630)
    }

    @Test
    fun `Part 2 solution works for sample data`() {
        assertThat(Day04(resourceAsList("day04-sample.txt")).part2()).isEqualTo(4455)
    }

    @Tag("Solution")
    @Test()
    fun `Part 2 solution`() {
        assertThat(Day04(resourceAsList("day04.txt")).part2()).isEqualTo(136571)
    }
}