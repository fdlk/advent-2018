package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day03Test {
    @Test()
    fun `Parses input correctly`() {
        Assertions.assertThat(Day03.Claim.parse("#1 @ 935,649: 22x22"))
                .isEqualTo(Day03.Claim("#1", 935, 649, 22, 22))
    }

    @Test()
    fun `Claim has correct ranges`() {
        Assertions.assertThat(Day03.Claim.parse("#123 @ 3,2: 5x4").xRange).isEqualTo(3 until 8)
        Assertions.assertThat(Day03.Claim.parse("#123 @ 3,2: 5x4").yRange).isEqualTo(2 until 6)
    }

    @Test()
    fun `Claim includes is correct`() {
        val claim = Day03.Claim.parse("#123 @ 3,2: 5x4")
        Assertions.assertThat(claim.contains(2, 1)).isFalse()
        Assertions.assertThat(claim.contains(3, 2)).isTrue()
        Assertions.assertThat(claim.contains(7, 5)).isTrue()
        Assertions.assertThat(claim.contains(8, 6)).isFalse()
    }

    @Test()
    fun `Part 1 and 2 solutions`() {
        val day03 = Day03(resourceAsList("day03.txt"))
        println(day03.part1())
        println(day03.part2())
    }
}