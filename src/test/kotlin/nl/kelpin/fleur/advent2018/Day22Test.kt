package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day22Test {
    companion object {
        val day22 = Day22(11739, Point(11, 718))
        val sample = Day22(510, Point(10, 10))
    }
    @Test
    fun sampleRisk(){
        assertThat(sample.part1()).isEqualTo(114)
    }

    @Test
    fun actualRisk(){
        assertThat(day22.part1()).isEqualTo(114)
    }
}