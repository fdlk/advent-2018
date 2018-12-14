package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Day14Test {

    @Test
    fun `Part 1 examples`() {
        assertThat(Day14().part1(9)).isEqualTo("5158916779")
        assertThat(Day14().part1(5)).isEqualTo("0124515891")
        assertThat(Day14().part1(18)).isEqualTo("9251071085")
        assertThat(Day14().part1(2018)).isEqualTo("5941429882")
    }

    @Test
    @Tag("Solution")
    fun `Part 1 solution`() {
        assertThat(Day14().part1(77201)).isEqualTo("9211134315")
    }

    @Test
    fun `Part 2 examples`() {
        assertThat(Day14().part2("51589")).isEqualTo(9)
        assertThat(Day14().part2("01245")).isEqualTo(5)
        assertThat(Day14().part2("92510")).isEqualTo(18)
        assertThat(Day14().part2("59414")).isEqualTo(2018)
    }

    @Test
    @Tag("Solution")
    fun `Part 2 solution`() {
        assertThat(Day14().part2("077201")).isEqualTo(20357548)
    }
}