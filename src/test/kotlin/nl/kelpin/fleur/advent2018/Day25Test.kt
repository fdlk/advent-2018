package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day25Test {
    val sample1 = Day25(resourceAsList("day25-sample1.txt"))
    val sample2 = Day25(resourceAsList("day25-sample2.txt"))
    val sample3 = Day25(resourceAsList("day25-sample3.txt"))
    val sample4 = Day25(resourceAsList("day25-sample4.txt"))
    val actual = Day25(resourceAsList("day25.txt"))

    @Test
    fun `sample 1`(){
        assertThat(sample1.part1()).isEqualTo(2)
    }

    @Test
    fun `sample 2`(){
        assertThat(sample2.part1()).isEqualTo(4)
    }

    @Test
    fun `sample 3`(){
        assertThat(sample3.part1()).isEqualTo(3)
    }

    @Test
    fun `sample 4`(){
        assertThat(sample4.part1()).isEqualTo(8)
    }

    @Test
    fun `actual part 1`() {
        assertThat(actual.part1()).isEqualTo(394)
    }
}