package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Day06Test {
    companion object {
        val sample = Day06(resourceAsList("day06-sample.txt"))
        val actual = Day06(resourceAsList("day06.txt"))
    }

    @Test
    fun `Loads default input`() {
        assertThat(sample.points).isEqualTo(listOf(
                Point(1, 1),
                Point(1, 6),
                Point(8, 3),
                Point(3, 4),
                Point(5, 5),
                Point(8, 9)
        ))
    }

    @Test
    fun `ranges`() {
        assertThat(sample.xRange).isEqualTo(0..9)
        assertThat(sample.yRange).isEqualTo(0..10)
    }

    @Test
    fun `manhattan distance is defined properly`() {
        val a = Point(1, 1)
        val b = Point(8, 9)
        assertThat(a.distanceTo(b)).isEqualTo(15)
        assertThat(b.distanceTo(a)).isEqualTo(15)
    }

    @Test
    fun `closest point`() {
        assertThat(sample.closestPoint(Point(3,2))).isEqualTo(Point(3, 4))
        assertThat(sample.closestPoint(Point(0,4))).isNull()
    }

    @Test
    fun `Part 1 sample data`() {
        assertThat(sample.part1()).isEqualTo(17)
    }

    @Tag("Solution")
    @Test
    fun `Part 1 solution`() {
        assertThat(actual.part1()).isEqualTo(17)
    }
}