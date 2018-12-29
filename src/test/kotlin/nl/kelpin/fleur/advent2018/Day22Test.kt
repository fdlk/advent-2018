package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day22Test {
    companion object {
        val actual = Day22(11739, Point(11, 718))
        val sample = Day22(510, Point(10, 10))
    }
    @Test
    fun `Sample part 1`(){
        assertThat(sample.part1()).isEqualTo(114)
    }

    @Test
    fun `Solution part 1`(){
        assertThat(actual.part1()).isEqualTo(8735)
    }

    @Test
    fun `Sample route`(){
        assertThat(sample.part2()).isEqualTo(45)
    }

    @Test
    fun `Solution part 2`(){
        assertThat(actual.part2()).isEqualTo(984)
    }
}