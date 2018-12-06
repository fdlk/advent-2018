package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Day06Test {
    @Test
    fun `Loads default input`() {
        assertThat(Day06(resourceAsList("day06-sample.txt")).points)
                .isEqualTo(listOf(
                        Day06.Point(1, 1),
                        Day06.Point(1, 6),
                        Day06.Point(8, 3),
                        Day06.Point(3, 4),
                        Day06.Point(5,5),
                        Day06.Point(8, 9)
                ))
    }
}