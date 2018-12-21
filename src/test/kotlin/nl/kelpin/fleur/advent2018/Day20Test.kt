package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test

class Day20Test {

    @Test
    fun `TreeParser tokenize`() {
        assertThat(Day20().TreeParser.tokenizer.tokenize("""^ENWWW(NEEE|SSE(EE|N))${'$'}""").toList()
                .map { it.text }.joinToString("")).isEqualTo("""^ENWWW(NEEE|SSE(EE|N))${'$'}""")
    }

    @Test
    fun `sample2 part 1`() {
        assertThat(Day20().part1("""^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN${'$'}""")).isEqualTo(18)
    }

    @Test
    fun `sample1 part 1`() {
        assertThat(Day20().part1("""^ENWWW(NEEE|SSE(EE|N))${'$'}""")).isEqualTo(10)
    }

    @Test
    fun `solution both parts`() {
        val day20 = Day20()
        val input = resourceAsList("day20.txt")[0]
        assertThat(day20.part1(input)).isEqualTo(3669)
        assertThat(day20.part2.size).isEqualTo(8369)
    }
}