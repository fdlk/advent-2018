package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

class Day10Test {

    @Test
    fun `Test Example part 1`() {
        assertThat(Day10(resourceAsList("day10-sample.txt")).findAlignment(1..10)).isEqualTo(3)
    }

    @Tag("Solution")
    @Test
    fun `Solution both parts`() {
        assertThat(Day10(resourceAsList("day10.txt")).findAlignment(10000..20000)).isEqualTo(10813)
    }
}