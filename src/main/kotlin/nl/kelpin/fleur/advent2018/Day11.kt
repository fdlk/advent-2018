package nl.kelpin.fleur.advent2018

class Day11(val gridSerialNumber: Int) {
    fun powerLevel(point: Point): Int =
            with(point) {
                val rackID = x + 10
                val multiplied = (rackID * y + gridSerialNumber) * rackID
                val hundredDigit = (multiplied % 1000) / 100
                return hundredDigit - 5
            }

    fun totalPower(topLeft: Point, n:Int = 3): Int =
            (0 until n).flatMap { dx ->
                (0 until n).map { dy ->
                    topLeft.plus(Point(dx, dy))
                }
            }.map(::powerLevel).sum()

    fun part1(): Point? = (1..298).flatMap{x -> (1..298).map{y -> Point(x, y)}}.maxBy{totalPower(it)}

    fun part2() = (10..20).flatMap{ n ->
        (1 .. 300 - n + 1).flatMap{x ->
            (1 .. 300 - n + 1).map{y ->
                Point(x, y) to n
            }}}.maxBy{(topLeft, n) -> totalPower(topLeft, n)}
}