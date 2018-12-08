package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Day08Test {
    companion object {
        val sample = Day08(resourceAsString("day08-sample.txt"))
        val actual = Day08(resourceAsString("day08.txt"))
    }

    @Test
    fun `Single node`() {
        assertThat(Day08("0 1 99").part1()).isEqualTo(99)
    }

    @Test
    fun `One child node`() {
        assertThat(Day08("1 0 0 1 99").part1()).isEqualTo(99)
    }

    @Test
    fun `Part 1 sample input`() {
        assertThat(sample.part1()).isEqualTo(138)
    }

    @Tag("Solution")
    @Test
    fun `Part 1`() {
        assertThat(actual.part1()).isEqualTo(42798)
    }

    @Test
    fun `parses tree`() {
        assertThat(sample.tree).isEqualTo(
                Day08.Node(listOf(
                        Day08.Node(emptyList(), listOf(10, 11, 12)),
                        Day08.Node(listOf(
                                Day08.Node(emptyList(), listOf(99))
                        ), listOf(2))),
                        listOf(1, 1, 2)))
    }

    @Test
    fun `value no children`() {
        assertThat(Day08.Node(emptyList(), listOf(10, 11, 12)).value()).isEqualTo(33)
    }

    @Test
    fun `Part 2 sample input`() {
        assertThat(sample.part2()).isEqualTo(66)
    }

    @Tag("Solution")
    @Test
    fun `Part 2`() {
        assertThat(actual.part2()).isEqualTo(23798)
    }
}