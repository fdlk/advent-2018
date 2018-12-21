package nl.kelpin.fleur.advent2018

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.parser.Parser

sealed class DungeonNode
data class Alternatives(val options: List<DungeonNode>) : DungeonNode()
data class Moves(val moves: String) : DungeonNode() {
    fun opposite(c: Char): Char = when (c) {
        'N' -> 'S'
        'E' -> 'W'
        'S' -> 'N'
        'W' -> 'E'
        else -> throw IllegalStateException()
    }

    fun plus(c: Char) = when {
        moves.endsWith(opposite(c)) -> Moves(moves.dropLast(1))
        else -> Moves(moves + c)
    }
}

class Day20 {
    val TreeParser = object : Grammar<Alternatives>() {
        val regexStart = token("""\^""")
        val regexEnd = token("""\$""")
        val lpar = token("""\(""")
        val rpar = token("""\)""")
        val or = token("""\|""")
        val direction = token("[NEWS]")

        override val tokens = listOf(regexStart, regexEnd, lpar, rpar, or, direction)

        val moves: Parser<Moves> by oneOrMore(direction) map { Moves(it.map { it.text }.joinToString("")) }
        val alternatives: Parser<List<DungeonNode>> by separatedTerms(optional(parser(::movesOrAlternatives)), or) map {
            it.map { it ?: Moves("") }
        }
        val alternativesInBrackets: Parser<Alternatives> by (-lpar and alternatives and -rpar) map { Alternatives(it) }
        val movesOrAlternatives: Parser<Alternatives> by oneOrMore(moves or alternativesInBrackets) map { Alternatives(it) }

        override val rootParser by (-regexStart * movesOrAlternatives * -regexEnd)
    }

    fun parse(input: String): Alternatives = TreeParser.parseToEnd(input)

    var part2: MutableSet<String> = mutableSetOf()

    fun reduceMoves(acc: Pair<Int, Moves>, char: Char): Pair<Int, Moves> {
        val (maxDist, moves) = acc
        val moved = moves.plus(char)
        if (moved.moves.length >= 1000) {
            part2.add(moved.moves)
        }
        return Math.max(maxDist, moved.moves.length) to moved
    }

    fun maxDepth(deepest: Int, current: Moves, tree: Alternatives): Int {
        val firstInTree = tree.options.firstOrNull()
        when (firstInTree) {
            null -> return Math.max(deepest, current.moves.length)
            is Moves -> {
                val (maxDepthAfterMoving, afterMoving) = firstInTree.moves.fold(deepest to current, ::reduceMoves)
                return maxDepth(maxDepthAfterMoving, afterMoving, Alternatives(tree.options.drop(1)))
            }
            is Alternatives -> {
                val maxDepthInFirstTree = maxDepth(deepest, current, firstInTree)
                return maxDepth(maxDepthInFirstTree, current, Alternatives(tree.options.drop(1)))
            }
        }
    }

    fun part1(input: String): Int = maxDepth(0, Moves(""), parse(input))
}