package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Day07Test {
    companion object {
        val sample = Day07(resourceAsList("day07-sample.txt"), 0, 2)
        val actual = Day07(resourceAsList("day07.txt"))
    }

    @Test
    fun `Loads default input`() {
        assertThat(sample.dependencies).isEqualTo(listOf(
                Pair('A', setOf('C')),
                Pair('B', setOf('A')),
                Pair('C', setOf()),
                Pair('D', setOf('A')),
                Pair('E', setOf('B', 'D', 'F')),
                Pair('F', setOf('C'))
                ).toMap())
    }

    @Test
    fun `Finds next step`() {
        assertThat(sample.nextStep("")).isEqualTo('C')
        assertThat(sample.nextStep("C")).isEqualTo('A')
        assertThat(sample.nextStep("CA")).isEqualTo('B')
    }

    @Test
    fun `Part 1 sample data`() {
        assertThat(sample.part1()).isEqualTo("CABDFE")
    }

    @Tag("Solution")
    @Test
    fun `Part 1 solution`() {
        assertThat(actual.part1()).isEqualTo("ADEFKLBVJQWUXCNGORTMYSIHPZ")
    }

    @Test
    fun `Part 2 sample data`() {
        assertThat(sample.part2()).isEqualTo(15)
    }

    @Tag("Solution")
    @Test
    fun `Part 2 solution`() {
        assertThat(actual.part2()).isEqualTo(1120)
    }
}