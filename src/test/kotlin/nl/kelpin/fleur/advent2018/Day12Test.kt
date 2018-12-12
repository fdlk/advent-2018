package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

class Day12Test {
    val sample = Day12.of(resourceAsList("day12-sample.txt"))

    @Test
    fun `of`() {
        assertThat(sample.initialState).isEqualTo("#..#.#..##......###...###")
        assertThat(sample.rules.size).isEqualTo(14)
        assertThat(sample.rules.toList().contains(".####" to '#'))
    }

    @Test
    fun `next`() {
        assertThat(sample.next(sample.initialState, 0)).isEqualTo("#...#....#.....#..#..#..#" to 0)
    }

    @Test
    fun `Part 1 sample`() {
        assertThat(sample.part1()).isEqualTo(325)
    }

    @Test
    @Tag("Solution")
    fun `Part 1 Solution`() {
        assertThat(Day12.of(resourceAsList("day12.txt")).part1()).isEqualTo(3258)
    }

    @Test
    @Tag("Solution")
    fun `Part 2 Solution`() {
        assertThat(Day12.of(resourceAsList("day12.txt")).part2()).isEqualTo(3600000002022L)
    }
}