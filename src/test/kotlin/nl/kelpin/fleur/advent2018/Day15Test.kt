package nl.kelpin.fleur.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day15Test {
    val actual = Day15(resourceAsList("day15.txt"))
    val sample = Day15(resourceAsList("day15-sample.txt"))
    val sample2 = Day15(resourceAsList("day15-sample2.txt"))
    val sample3 = Day15(resourceAsList("day15-sample3.txt"))
    val sample4 = Day15(resourceAsList("day15-sample4.txt"))
    val sample5 = Day15(resourceAsList("day15-sample5.txt"))

    @Test
    fun `cave matches isOpen`() {
        println(actual.cave.joinToString("\n"))
    }

    @Test
    fun `xRange, yRange and isOpen produce same cave`() {
        assertThat(actual.mapWith(emptySet())).isEqualTo(actual.cave)
    }

    @Test
    fun `isOpen is false outside cave range`() {
        assertThat(actual.isOpenCave(Point(-1, -1))).isFalse()
        assertThat(actual.isOpenCave(Point(actual.xRange.endInclusive + 1, 0))).isFalse()
        assertThat(actual.isOpenCave(Point(0, actual.yRange.endInclusive + 1))).isFalse()
    }

    @Test
    fun `neighbors of point 6, 12 are left and right in that order`() {
        val p = Point(6, 10)
        assertThat(actual.neighbors(p)).isEqualTo(listOf(p.move(Left), p.move(Right)))
        assertThat(actual.neighbors(p)).isEqualTo(listOf(Point(5, 10), Point(7, 10)))
    }

    @Test
    fun `attack`() {
        val elf = Day15.Critter(Point(0, 0), 'E')
        val gobber = Day15.Critter(Point(1, 0), 'G')
        assertThat(gobber.attackedBy(elf)).isEqualTo(gobber.copy(hitPoints = 197))
    }

    @Test
    fun `sample elf move`() {
        val gnomes = sample.initialCritters.filter { it.type == 'G' }
        val elf = sample.initialCritters.filter { it.type == 'E' }.first()
        val expected = elf.location.move(Up)

        assertThat(sample.move(elf, gnomes.map { it.location }.toSet(), emptySet())).isEqualTo(expected)
    }

    @Test
    fun `sample gnome moves`() {
        val gnomes = sample.initialCritters.filter { it.type == 'G' }
        val elves = sample.initialCritters.filter { it.type == 'E' }

        assertThat(gnomes.take(3).map {
            sample.move(Day15.Critter(it.location, 'G'),
                    elves.map { it.location }.toSet(), gnomes.map { it.location }.toSet())
        }).isEqualTo(listOf(
                Point(2, 1),
                Point(4, 2),
                Point(6, 1)))
    }

    @Test
    fun `next`() {
        var critters = sample.initialCritters
        for (i in 1..47) {
            critters = sample.nextRound(critters).second
            println(i)
            println(sample.mapWith(critters))
            println(critters.sortedWith(sample.critterComparison).map { "${it.type}(${it.hitPoints})" }.joinToString())
        }
    }

    @Test
    fun `next sample 2`() {
        var critters = sample2.initialCritters
        for (i in 1..38) {
            critters = sample2.nextRound(critters).second
            println(i)
            println(sample2.mapWith(critters))
            println(critters.sortedWith(sample2.critterComparison).map { "${it.type}(${it.hitPoints})" }.joinToString())
        }
    }

    @Test
    fun `Part 1 Sample inputs`() {
        assertThat(sample.part1()).isEqualTo(27730)
        assertThat(sample2.part1()).isEqualTo(36334)
        assertThat(sample3.part1()).isEqualTo(27755)
        assertThat(sample4.part1()).isEqualTo(28944)
        assertThat(sample5.part1()).isEqualTo(18740)
    }

    @Test
    fun `Solution Part 1`() {
        // 226960 is too high
        // 224123 is too high
        assertThat(actual.part1()).isEqualTo(224123)
    }
}