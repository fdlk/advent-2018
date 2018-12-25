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
        assertThat(actual.part1()).isEqualTo(164)
    }

    @Test
    fun `sample part 2`() {
        assertThat(sample2.part2()).isEqualTo(36)
    }

    @Test
    fun `intRange split`() {
       assertThat((-3..3).split()).isEqualTo(setOf(-3..0, 1..3))
    }

    @Test
    fun `intRange size 2 split`() {
        assertThat((0..1).split()).isEqualTo(setOf(0..0, 1..1))
    }

    @Test
    fun `intRange size 1 split`() {
        assertThat((0..0).split()).isEqualTo(setOf(0..0))
    }

    @Test
    fun `initial Box Overlaps`() {
        assertThat(actual.overlappingSpheres(actual.initialBox())).isEqualTo(1000)
    }

    @Test
    fun `actual part 2`() {
        assertThat(actual.part2()).isEqualTo(122_951_778)
    }

    @Test
    fun `magne part 2`() {
        assertThat(Day23(resourceAsList("day23-magne.txt")).part2()).isEqualTo(71_484_642)
    }
}