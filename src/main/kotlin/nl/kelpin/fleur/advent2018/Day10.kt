package nl.kelpin.fleur.advent2018

class Day10(input: List<String>) {

    data class Star(val position: Point, val velocity: Point) {
        companion object {
            val starRE = Regex("""position=< ?(-?\d+), +(-?\d+)> velocity=< ?(-?\d+), +(-?\d+)>""")
            fun of(notation: String): Star? = starRE
                    .matchEntire(notation)
                    ?.destructured
                    ?.let { (x, y, dx, dy) ->
                        Star(Point(x.toInt(), y.toInt()), Point(dx.toInt(), dy.toInt()))
                    }
        }

        fun atTime(n: Int = 1): Star = Star(position.plus(velocity.times(n)), velocity)
    }

    data class Chart(val stars: List<Star>) {
        companion object {
            fun of(notation: List<String>): Chart = Chart(notation.mapNotNull(Star.Companion::of))
        }

        private fun charFor(p: Point): Char = if (stars.any { it.position == p }) '#' else ' '
        private fun xRange(): IntRange = stars.map { it.position.x }.range()
        private fun yRange(): IntRange = stars.map { it.position.y }.range()
        fun area(): Long = xRange().length().toLong() * yRange().length().toLong()
        fun atTime(n: Int = 1): Chart = Chart(stars.map { it.atTime(n) })
        override fun toString(): String = yRange().map { y ->
            xRange().map { x -> charFor(Point(x, y)) }.toCharArray().joinToString("")
        }.joinToString("\n")
    }

    private val initialState: Chart = Chart.of(input)

    fun findAlignment(steps: IntRange): Int? = steps.asSequence()
            .map { it to initialState.atTime(it) }
            .zipWithNext()
            .find { (current: Pair<Int, Chart>, next: Pair<Int, Chart>) -> next.second.area() > current.second.area() }
            ?.let { (current, _) -> current }
            ?.also { (_: Int, chart: Chart) -> println(chart) }
            ?.let { (time: Int, _: Chart) -> time }
}