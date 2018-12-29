package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day16Test {
    val actual = Day16(resourceAsList("day16.txt"), resourceAsList("day16-program.txt"))

    @Test
    fun `parses all samples`() {
        assertThat(actual.samples.last()).isEqualTo(Day16.Sample.of(listOf("Before: [0, 1, 2, 2]","3 2 2 1","After:  [0, 2, 2, 2]")))
    }

    @Test
    fun `tests all operations`() {
        assertThat(Day16.operations.toSet().size).isEqualTo(16)
    }

    @Test
    fun `Sample is ambiguous`() {
        val sample = Day16.Sample.of(listOf("Before: [3, 2, 1, 1]","9 2 1 2","After:  [3, 2, 2, 1]"))
        assertThat(sample.isAmbiguous()).isTrue()
    }

    @Test
    fun `resolveMappings`() {
        actual.resolveMappings().forEach { t, u -> println("$t, $u") }
    }

    @Test
    fun `Solution Part 1`() {
        assertThat(actual.part1()).isEqualTo(517)
    }

    @Test
    fun `Solution Part 2`() {
        assertThat(actual.part2()).isEqualTo(667)
    }
}