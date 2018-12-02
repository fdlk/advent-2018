package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day02Test {
    @Test()
    fun `Counts twos and threes correctly`() {
        Assertions.assertThat(TwosAndThrees("abcdef")).isEqualTo(TwosAndThrees(0, 0))
        Assertions.assertThat(TwosAndThrees("bababc")).isEqualTo(TwosAndThrees(1, 1))
        Assertions.assertThat(TwosAndThrees("abbcde")).isEqualTo(TwosAndThrees(1, 0))
        Assertions.assertThat(TwosAndThrees("abcccd")).isEqualTo(TwosAndThrees(0, 1))
        Assertions.assertThat(TwosAndThrees("aabcdd")).isEqualTo(TwosAndThrees(1, 0))
        Assertions.assertThat(TwosAndThrees("abcdee")).isEqualTo(TwosAndThrees(1, 0))
        Assertions.assertThat(TwosAndThrees("ababab")).isEqualTo(TwosAndThrees(0, 1))
    }

    @Test()
    fun `Part 1 matches example`() {
        Assertions.assertThat(Day02(listOf("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab")).part1()).isEqualTo(12)
    }

    @Test()
    fun `Part 1 solution`() {
        println(Day02(resourceAsList("day02.txt")).part1())
    }

    @Test()
    fun `Finds matching chars in two strings`() {
        Assertions.assertThat("fghij".matchingChars("fguij")).isEqualTo("fgij")
        Assertions.assertThat("abcde".matchingChars("axcye")).isEqualTo("ace")
    }

    @Test()
    fun `Part 2 solution`() {
        println(Day02(resourceAsList("day02.txt")).part2())
    }

}