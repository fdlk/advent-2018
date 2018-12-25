package nl.kelpin.fleur.advent2018

import nl.kelpin.fleur.advent2018.Day24.Companion.IMMUNE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day24Test {
    val sample = Day24(
            resourceAsList("day24-sample-immune.txt"),
            resourceAsList("day24-sample-infection.txt"))
    val actual = Day24(
            resourceAsList("day24-immune.txt"),
            resourceAsList("day24-infection.txt"))

    @Test
    fun `parse`() {
        // 17 units each with 5390 hit points (weak to radiation, bludgeoning)
        // with an attack that does 4507 fire damage at initiative 2
        assertThat(sample.groups.first{it.side == IMMUNE}).isEqualTo(
                Day24.Group("immune", 1, 17, 5390,
                        listOf(Day24.Susceptibility("weak",
                                listOf("radiation", "bludgeoning"))),
                        4507,
                        "fire",
                        2)
        )
    }

    @Test
    fun `Effective power`() {
        val group = Day24.Group.parse("immune", "18 units each with 729 hit points (weak to fire; immune to cold, slashing)" +
                " with an attack that does 8 radiation damage at initiative 10", 1)
        assertThat(group.effectivePower()).isEqualTo(144)
    }

    @Test
    fun `Target acquisition order`() {
        val group = Day24.Group.parse("immune", "18 units each with 729 hit points (weak to fire; immune to cold, slashing)" +
                " with an attack that does 8 radiation damage at initiative 10", 1)
        val groupWithHigherInitiative = group.copy(initiative = group.initiative + 1)
        val groupWithLowerEffectivePowerAndHigherInitiative = groupWithHigherInitiative.copy(damage = group.damage - 1)
        val groups = listOf(group, groupWithHigherInitiative, groupWithLowerEffectivePowerAndHigherInitiative)
        assertThat(groups.sortedWith(Day24.mostEffectivePowerThenHighestInitiative))
                .isEqualTo(listOf(groupWithHigherInitiative, group, groupWithLowerEffectivePowerAndHigherInitiative))
    }

    @Test
    fun `Target selection`() {
        assertThat(Day24.State(sample.groups).targetSelection()).isEqualTo(
                mapOf("infection #2" to "immune #2",
                        "immune #2" to "infection #1",
                        "immune #1" to "infection #2",
                        "infection #1" to "immune #1")
        )
    }

    @Test
    fun `sample next`() {
        val next = Day24.State(sample.groups).next()
    }

    @Test
    fun `sample part1`() {
        assertThat(sample.part1()).isEqualTo(5216)
    }

    @Test
    fun `actual part1`() {
        assertThat(actual.part1()).isEqualTo(9328)
    }

    @Test
    fun `actual part2`() {
        assertThat(actual.part2(0)).isEqualTo(2172)
    }
}