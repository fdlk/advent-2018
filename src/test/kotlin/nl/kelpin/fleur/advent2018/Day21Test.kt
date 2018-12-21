package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day21Test {
    // what silly nonsense was this?!?
    val actual = Day19(resourceAsList("day21.txt"), 5)

    @Test
    fun `part1 solution`() {
        assertThat(actual.regses(listOf(0, 0, 0, 0, 0, 0)).find { it[5] == 28 }!![2]).isEqualTo(10780777)
    }

    @Test
    fun `part2 solution`() {
        actual.regses(listOf(0, 207, 3308537, 1, 1, 28)).drop(1).filter { it[5] == 28 }.find { println(it[2]); it[2] == 3308537 }
    }
}