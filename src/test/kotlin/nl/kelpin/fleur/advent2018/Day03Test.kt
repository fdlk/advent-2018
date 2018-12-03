package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

class Day03Test {
    @Test()
    fun `Parses input correctly`() {
        assertThat(Day03.Claim.parse("#1 @ 935,649: 22x22"))
                .isEqualTo(Day03.Claim(1, 935 until 957, 649 until 671))
    }

    @Test()
    fun `Claim has correct ranges`() {
        assertThat(Day03.Claim.parse("#123 @ 3,2: 5x4").xRange).isEqualTo(3 until 8)
        assertThat(Day03.Claim.parse("#123 @ 3,2: 5x4").yRange).isEqualTo(2 until 6)
    }

    @Test()
    fun `Claim squares are correct`() {
        val squares = Day03.Claim.parse("#123 @ 3,2: 5x4").squares()
        assertThat(squares.size).isEqualTo(20)
        assertThat(squares.contains(Pair(2, 1))).isFalse()
        assertThat(squares.contains(Pair(3, 2))).isTrue()
        assertThat(squares.contains(Pair(7, 5))).isTrue()
        assertThat(squares.contains(Pair(8, 6))).isFalse()
    }

    @Tags(Tag("Solution"))
    @Test()
    fun `Part 1 solution`() {
        val day03 = Day03(resourceAsList("day03.txt"))
        assertThat(day03.part1()).isEqualTo(111326)
    }

    @Tags(Tag("Solution"))
    @Test()
    fun `Part 2 solution`() {
        val day03 = Day03(resourceAsList("day03.txt"))
        assertThat(day03.part2()).isEqualTo(1019)
    }
}