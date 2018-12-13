package nl.kelpin.fleur.advent2018

sealed class Turn
object TurnLeft : Turn()
object TurnRight : Turn()
object Straight : Turn()

class Day13(input: List<String>, val removeCrashedCarts: Boolean = false) {
    companion object {
        fun replaceCartWithTrack(cart: Char): Char = when (cart) {
            '>', '<' -> '-'
            '^', 'v' -> '|'
            else -> cart
        }
    }

    val track: List<String> = input.map { it.map { replaceCartWithTrack(it) }.joinToString("") }
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
    }.flatMap { it }.toSet()

    data class Cart(val location: Point, val direction: Direction, val nextTurn: Turn = TurnLeft) {
        fun newNextTurn(charAtNewPos: Char): Turn = when (charAtNewPos) {
            '+' -> when (nextTurn) {
                TurnLeft -> Straight
                Straight -> TurnRight
                TurnRight -> TurnLeft
            }
            else -> nextTurn
        }

        fun velocity(): Point = when (direction) {
            Left -> Point(-1, 0)
            Up -> Point(0, -1)
            Right -> Point(1, 0)
            Down -> Point(0, 1)
        }

        fun newDirection(charAtNewPos: Char): Direction = when (charAtNewPos) {
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
    }

    fun next(cart: Cart): Cart = with(cart) {
        val nextLocation = location.plus(velocity())
        val charAtNewPos = track[nextLocation.y][nextLocation.x]
        return copy(location = nextLocation, direction = newDirection(charAtNewPos), nextTurn = newNextTurn(charAtNewPos))
    }

    fun next(carts: Set<Cart>): Pair<Point?, Set<Cart>> {
        val sorted: MutableList<Cart> = carts.sortedWith(compareBy({ it.location.y }, { it.location.x })).toMutableList()
        val moved: MutableSet<Cart> = mutableSetOf()
        while (!sorted.isEmpty()) {
            val cart = next(sorted.removeAt(0))
            if (moved.any { it.location == cart.location } || sorted.any { it.location == cart.location }) {
                if (!removeCrashedCarts) {
                    return cart.location to emptySet()
                } else {
                    moved.removeIf{ it.location == cart.location }
                    sorted.removeIf { it.location == cart.location }
                }
            } else moved.add(cart)
        }
        return null to moved
    }

    tailrec fun part1(carts: Set<Cart> = initialCarts): Point {
        val (collision, updated) = next(carts)
        return if (collision != null) {
            collision
        } else part1(updated)
    }

    tailrec fun part2(carts: Set<Cart> = initialCarts): Point {
        val (_, updated) = next(carts)
        return if (updated.size == 1) {
            updated.first().location
        } else part2(updated)
    }
}