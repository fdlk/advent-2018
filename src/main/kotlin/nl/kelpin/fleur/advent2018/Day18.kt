package nl.kelpin.fleur.advent2018

class Day18(val input: List<String>) {

    private fun insideGrid(point: Point, state: List<String>): Boolean = with(point) {
        (0 until state.size).contains(y) && (0 until state[0].length).contains(x)
    }

    private fun charAt(point: Point, state: List<String>): Char = with(point) { state[y][x] }

    private fun neighbors(point: Point, state: List<String>): List<Char> = listOf(
            point.move(Up).move(Left),
            point.move(Up),
            point.move(Up).move(Right),
            point.move(Right),
            point.move(Right).move(Down),
            point.move(Down),
            point.move(Down).move(Left),
            point.move(Left))
            .filter { insideGrid(it, state) }
            .map { charAt(it, state) }

    private fun nextChar(char: Char, point: Point, state: List<String>): Char {
        val neighborChars = neighbors(point, state).groupingBy { it }.eachCount()
        return when (char) {
            '.' -> when {
                neighborChars.getOrDefault('|', 0) >= 3 -> '|'
                else -> '.'
            }
            '|' -> when {
                neighborChars.getOrDefault('#', 0) >= 3 -> '#'
                else -> '|'
            }
            '#' -> when {
                neighborChars.contains('#') && neighborChars.contains('|') -> '#'
                else -> '.'
            }
            else -> throw IllegalStateException()
        }
    }

    private fun states() = sequence {
        var state = input
        while (true) {
            yield(state)
            state = state.mapIndexed { y, line ->
                line.toList().mapIndexed { x, char ->
                    nextChar(char, Point(x, y), state)
                }.joinToString("")
            }
        }
    }


    fun value(state: List<String>): Int {
        val characters = state.map { it.toList() }.flatten().groupingBy { it }.eachCount()
        return characters['#']!! * characters['|']!!
    }

    fun part1(n: Int = 10): Int = value(states().drop(n).first())

    fun part2(n: Int = 1000000000): Int {
        val seen = mutableSetOf<List<String>>()
        val firstRepeater = states().find { !seen.add(it) }
        val firstSeen = states().indexOfFirst { it == firstRepeater }
        val period = states().drop(firstSeen + 1).indexOfFirst { it == firstRepeater }
        val leftover = (n - firstSeen) % (period + 1)
        return value(states().drop(firstSeen + leftover).first())
    }
}