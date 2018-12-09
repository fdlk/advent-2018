package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

class Day09Test {

    @Test
    fun `Marbles circular next and previous`() {
        val marbles = Day09.Marbles(listOf(0, 1, 2))
        assertThat(marbles.circularNext() == 0)
        assertThat(marbles.circularNext() == 1)
        assertThat(marbles.circularNext() == 2)
        assertThat(marbles.circularNext() == 0)
        assertThat(marbles.circularPrevious() == 0)
        assertThat(marbles.circularPrevious() == 2)
        assertThat(marbles.circularPrevious() == 1)
        assertThat(marbles.circularPrevious() == 0)
    }

    @Test
    fun `Test Example part 1`() {
        assertThat(Day09(9, 25).highscore()).isEqualTo(32)
    }

    @Test
    fun `Test other examples part 1`() {
        assertThat(Day09(10, 1618).highscore()).isEqualTo(8317)
        assertThat(Day09(13, 7999).highscore()).isEqualTo(146373)
        assertThat(Day09(17, 1104).highscore()).isEqualTo(2764)
        assertThat(Day09(21, 6111).highscore()).isEqualTo(54718)
        assertThat(Day09(30, 5807).highscore()).isEqualTo(37305)
    }

    @Tag("Solution")
    @Test
    fun `Solution part 1`() {
        assertThat(Day09(403, 71920).highscore()).isEqualTo(439089L)
    }

    @Tag("Solution")
    @Test
    fun `Solution part 2`() {
        assertThat(Day09(403, 7192000).highscore()).isEqualTo(3668541094L)
    }
}