package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

class Day11Test {
    @Test
    fun `power level`() {
        assertThat(Day11(8).powerLevel(Point(3, 5))).isEqualTo(4)
        assertThat(Day11(57).powerLevel(Point(122,79))).isEqualTo(-5)
        assertThat(Day11(39).powerLevel(Point(217,196))).isEqualTo(0)
        assertThat(Day11(71).powerLevel(Point(101,153))).isEqualTo(4)
    }

    @Test
    fun `total power`() {
        assertThat(Day11(18).totalPower(Point(33,45))).isEqualTo(29)
        assertThat(Day11(42).totalPower(Point(21,61))).isEqualTo(30)
    }

    @Test
    fun `part 1 examples`() {
        assertThat(Day11(18).part1()).isEqualTo(Point(33,45))
        assertThat(Day11(42).part1()).isEqualTo(Point(21,61))
    }

    @Tag("Solution")
    @Test
    fun `part 1 Solution`() {
        assertThat(Day11(3613).part1()).isEqualTo(Point(20,54))
    }

    @Test
    fun `part 2 total power examples`() {
        assertThat(Day11(18).totalPower(Point(90,269), 16)).isEqualTo(113)
        assertThat(Day11(42).totalPower(Point(232,251), 12)).isEqualTo(119)
    }

    @Test
    fun `part 2 examples`() {
        assertThat(Day11(18).part2()).isEqualTo(Point(90,269) to 16)
        assertThat(Day11(42).part2()).isEqualTo(Point(232,251) to 12)
    }

    @Tag("Solution")
    @Test
    fun `part 2 Solution`() {
        assertThat(Day11(3613).part2()).isEqualTo(Point(233,93) to 13)
    }
}