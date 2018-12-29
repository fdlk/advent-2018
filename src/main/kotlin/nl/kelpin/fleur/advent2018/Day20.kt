package nl.kelpin.fleur.advent2018

import java.util.*
import kotlin.math.max

class Day20 {
    companion object {
        fun opposite(c: Char): Char = when (c) {
            'N' -> 'S'
            'E' -> 'W'
            'S' -> 'N'
            'W' -> 'E'
            else -> throw IllegalStateException()
        }
    }

    fun bothParts(input: String): Pair<Int, Int> {
        val stack = Stack<String>()
        var current = ""
        var maxDepth = 0
        val deepRooms= mutableSetOf<String>()
        for(c in input) {
            when (c) {
                'N', 'S', 'E', 'W' -> when {
                    opposite(c) == current.lastOrNull() -> current = current.dropLast(1)
                    else -> {
                        current += c
                        if(current.length >= 1000){
                            deepRooms.add(current)
                        }
                        maxDepth = max(maxDepth, current.length)
                    }
                }
                '(' -> stack.push(current)
                ')' -> current = stack.pop()
                '|' -> current = stack.peek()
            }
        }
        return maxDepth to deepRooms.size
    }
}