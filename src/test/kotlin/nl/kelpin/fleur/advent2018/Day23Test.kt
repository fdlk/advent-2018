package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day23Test {
    val sample = Day23(resourceAsList("day23-sample.txt"))
    val sample2 = Day23(resourceAsList("day23-sample2.txt"))
    val actual = Day23(resourceAsList("day23.txt"))

    @Test
    fun `sample part 1`() {
        assertThat(sample.part1()).isEqualTo(7)
    }

    @Test
    fun `actual part 1`() {
        assertThat(actual.part1()).isEqualTo(7)
    }

    @Test
    fun `sample part 2`() {
        assertThat(sample2.part2()).isEqualTo(36)
    }

    @Test
    fun `actual part 2`() {
        assertThat(actual.part2(10_000_000)).isEqualTo(122_951_778)
    }
}