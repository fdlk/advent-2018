package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day01Test {
    @Test()
    fun `Part 1 matches example`() {
        Assertions.assertThat(Day01(listOf("+1", "+1", "+1")).part1()).isEqualTo(3)
        Assertions.assertThat(Day01(listOf("+1", "+1", "-2")).part1()).isEqualTo(0)
        Assertions.assertThat(Day01(listOf("-1", "-2", "-3")).part1()).isEqualTo(-6)
    }

    @Test()
    fun `Part 1 solution`() {
        println(Day01(resourceAsList("day01.txt")).part1())
    }

    @Test()
    fun `Part 2 solution`() {
        println(Day01(resourceAsList("day01.txt")).part2())
    }
}