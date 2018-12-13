package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

class Day13Test {
    val sample = Day13(resourceAsList("day13-sample.txt"))

    @Test
    fun `Read track`() {
        println(sample.track.joinToString("\n"))
    }

    @Test
    fun `Read carts`() {
        assertThat(sample.initialCarts).isEqualTo(
                setOf(Day13.Cart(Point(2, 0), Right), Day13.Cart(Point(9, 3), Down)))
    }

    @Test
    fun `Next state`() {
        assertThat(sample.next(sample.initialCarts)).isEqualTo(
                null to setOf(Day13.Cart(Point(3, 0), Right), Day13.Cart(Point(9, 4), Right, Straight)))
    }

    @Test
    fun `Part 1 example`() {
        assertThat(sample.part1()).isEqualTo(Point(7, 3))
    }

    @Tag("Solution")
    @Test
    fun `Part 1 solution`() {
        assertThat( Day13(resourceAsList("day13.txt")).part1()).isEqualTo(Point(5, 102))
    }

    @Tag("Solution")
    @Test
    fun `Part 2 solution`() {
        assertThat( Day13(resourceAsList("day13.txt"), true).part2()).isEqualTo(Point(46, 45))
    }
}