package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day18Test {
    val actual=Day18(resourceAsList("day18.txt"))
    val sample=Day18(resourceAsList("day18-sample.txt"))

    @Test
    fun `part1 sample`() {
        assertThat(sample.part1()).isEqualTo(1147)
    }

    @Test
    fun `part1 solution`() {
        assertThat(actual.part1()).isEqualTo(644640)
    }

    @Test
    fun `part2 solution`() {
        assertThat(actual.part2()).isEqualTo(191080)
    }
}