package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day19Test {
    val sample=Day19(resourceAsList("day19-sample.txt"), 0)
    val actual=Day19(resourceAsList("day19.txt"), 2)

    @Test
    fun `part1 sample`() {
        assertThat(sample.part1()).isEqualTo(6)
    }

    @Test
    fun `part1 solution`() {
        assertThat(actual.part1()).isEqualTo(1728)
    }

    @Test
    fun `part2 solution`() {
        assertThat(actual.part2()).isEqualTo(18200448)
    }
}