package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day17Test {
    val actual=Day17(resourceAsList("day17.txt"))
    val sample=Day17(resourceAsList("day17-sample.txt"))

    @Test
    fun `clay range blocks inclusive`() {
        assertThat(Horizontal(495..501, 7).contains(Point(501, 7))).isTrue()
    }

    @Test
    fun `clay gets parsed`() {
        assertThat(sample.horizontals).contains(Horizontal(495..501, 7))
    }

    @Test
    fun `sample map`() {
        println(sample.map())
    }

    @Test
    fun `actual map`() {
        println(actual.map())
    }

    @Test
    fun `sample both parts`() {
        assertThat(sample.bothParts()).isEqualTo(57 to 29)
    }

    @Test
    fun `actual both parts`() {
        assertThat(actual.bothParts()).isEqualTo(31412 to 25857)
    }
}