package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test

class Day20Test {

    @Test
    fun `sample2 part 1`() {
        assertThat(Day20().bothParts("""^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN${'$'}""").first).isEqualTo(18)
    }

    @Test
    fun `sample1 part 1`() {
        assertThat(Day20().bothParts("""^ENWWW(NEEE|SSE(EE|N))${'$'}""").first).isEqualTo(10)
    }

    @Test
    fun `solution both parts`() {
        val day20 = Day20()
        val input = resourceAsList("day20.txt")[0]
        assertThat(day20.bothParts(input)).isEqualTo(3669 to 8369)
    }
}