package nl.kelpin.fleur.advent2018

sealed class Turn
object TurnLeft : Turn()
object TurnRight : Turn()
object Straight : Turn()

class Day13(input: List<String>, private val removeCrashedCarts: Boolean = false) {
    companion object {
        fun replaceCartWithTrack(cart: Char): Char = when (cart) {
            '>', '<' -> '-'
            '^', 'v' -> '|'
            else -> cart
        }

        val readingOrder = compareBy<Cart>({ it.location.y }, { it.location.x })
    }

    val track: List<String> = input.map { it.map(Companion::replaceCartWithTrack).joinToString("") }
    val initialCarts: Set<Cart> = input.mapIndexed { y, row ->
        row.mapIndexed { x, char ->
            when (char) {
                '>' -> Cart(Point(x, y), Right)
                '<' -> Cart(Point(x, y), Left)
                '^' -> Cart(Point(x, y), Up)
                'v' -> Cart(Point(x, y), Down)
                else -> null
            }
        }.filterNotNull()
    }.flatten().toSet()

    data class Cart(val location: Point, val direction: Direction, val nextTurn: Turn = TurnLeft) {
        private fun newNextTurn(charAtNewPos: Char): Turn = when (charAtNewPos) {
            '+' -> when (nextTurn) {
                TurnLeft -> Straight
                Straight -> TurnRight
                TurnRight -> TurnLeft
            }
            else -> nextTurn
        }

        private fun newDirection(charAtNewPos: Char): Direction = when (charAtNewPos) {
            '\\' -> when (direction) {
                Right -> Down
                Up -> Left
                Left -> Up
                Down -> Right
            }
            '/' -> when (direction) {
                Up -> Right
                Left -> Down
                Right -> Up
                Down -> Left
            }
            '+' -> when (nextTurn) {
                TurnLeft -> when (direction) {
                    Left -> Down
                    Down -> Right
                    Right -> Up
                    Up -> Left
                }
                Straight -> direction
                TurnRight -> when (direction) {
                    Left -> Up
                    Up -> Right
                    Right -> Down
                    Down -> Left
                }
            }
            else -> direction
        }

        fun moveOn(track: List<String>): Cart = with(location.move(direction)) {
            val charAtNewPos = track[y][x]
            return copy(location = this, direction = newDirection(charAtNewPos), nextTurn = newNextTurn(charAtNewPos))
        }
    }

    fun next(carts: Set<Cart>): Pair<Point?, Set<Cart>> {
        val sorted: MutableList<Cart> = carts.sortedWith(readingOrder).toMutableList()
        val moved: MutableSet<Cart> = mutableSetOf()
        while (sorted.isNotEmpty()) {
            val movedCart = sorted.removeAt(0).moveOn(track)
            if (moved.union(sorted).any { it.location == movedCart.location }) {
                if (!removeCrashedCarts) {
                    return movedCart.location to emptySet()
                } else {
                    moved.removeIf { it.location == movedCart.location }
                    sorted.removeIf { it.location == movedCart.location }
                }
            } else moved.add(movedCart)
        }
        return null to moved
    }

    tailrec fun part1(carts: Set<Cart> = initialCarts): Point {
        val (collision, updated) = next(carts)
        return collision ?: part1(updated)
    }

    tailrec fun part2(carts: Set<Cart> = initialCarts): Point {
        val (_, updated) = next(carts)
        return if (updated.size == 1) updated.first().location
        else part2(updated)
    }
}