package nl.kelpin.fleur.advent2018

import kotlin.reflect.KFunction3

typealias Operation = KFunction3<Int, Int, List<Int>, Int>

class Day16(samples: List<String>, val program: List<String>) {
    companion object {
        private fun addr(a: Int, b: Int, r: List<Int>): Int = r[a] + r[b]
        private fun addi(a: Int, b: Int, r: List<Int>): Int = r[a] + b
        private fun mulr(a: Int, b: Int, r: List<Int>): Int = r[a] * r[b]
        private fun muli(a: Int, b: Int, r: List<Int>): Int = r[a] * b
        private fun banr(a: Int, b: Int, r: List<Int>): Int = r[a] and r[b]
        private fun bani(a: Int, b: Int, r: List<Int>): Int = r[a] and b
        private fun borr(a: Int, b: Int, r: List<Int>): Int = r[a] or r[b]
        private fun bori(a: Int, b: Int, r: List<Int>): Int = r[a] or b
        private fun setr(a: Int, b: Int, r: List<Int>): Int = r[a]
        private fun seti(a: Int, b: Int, r: List<Int>): Int = a
        private fun gtir(a: Int, b: Int, r: List<Int>): Int = if (a > r[b]) 1 else 0
        private fun gtri(a: Int, b: Int, r: List<Int>): Int = if (r[a] > b) 1 else 0
        private fun gtrr(a: Int, b: Int, r: List<Int>): Int = if (r[a] > r[b]) 1 else 0
        private fun eqir(a: Int, b: Int, r: List<Int>): Int = if (a == r[b]) 1 else 0
        private fun eqri(a: Int, b: Int, r: List<Int>): Int = if (r[a] == b) 1 else 0
        private fun eqrr(a: Int, b: Int, r: List<Int>): Int = if (r[a] == r[b]) 1 else 0
        val operations: List<Operation> = listOf(::addr, ::addi, ::mulr, ::muli, ::banr, ::bani, ::bori, ::borr, ::setr, ::seti, ::gtir, ::gtri, ::gtrr, ::eqir, ::eqri, ::eqrr)
    }

    data class Sample(val r: List<Int>, val opcode: Int, val a: Int, val b: Int, val output: Int) {
        companion object {
            val state: Regex = Regex("""\w+:\s+\[(\d), (\d), (\d), (\d)]""")
            fun of(lines: List<String>): Sample {
                val r = state.matchEntire(lines[0])!!.groupValues.drop(1).map { it.toInt() }
                val (opcode, a, b, c) = lines[1].split(" ").map { it.toInt() }
                val after = state.matchEntire(lines[2])!!.groupValues.drop(1).map { it.toInt() }
                val output = after[c]
                return Sample(r, opcode, a, b, output)
            }
        }

        fun operations() = operations.filter(::matchesOperation)
        fun matchesOperation(operation: Operation) = operation(a, b, r) == output
    }

    val samples: List<Sample> = samples.windowed(3, 4).map { Sample.of(it) }

    fun part1(): Int = samples.count { it.operations().size >= 3 }

    tailrec fun resolveMappings(soFar: Map<Int, Operation> = mapOf(),
                                operations: Set<Operation> = Day16.operations.toSet(),
                                opCodes: Set<Int> = samples.map { it.opcode }.toSet()): Map<Int, Operation> {
        val possibleMappings = samples
                .groupBy { it.opcode }
                .filterKeys { opCodes.contains(it) }
                .mapValues { opcodeSamples -> operations.filter { operation -> opcodeSamples.value.all { it.matchesOperation(operation) } } }
        val new = possibleMappings.filterValues { it.size == 1 }.map { it.key to it.value.first() }.toMap()
        return if (new.isEmpty()) soFar else resolveMappings(soFar + new, operations - soFar.values, opCodes)
    }

    fun part2(): Int {
        val instructionsByOpcode = resolveMappings()
        val registry = mutableListOf(0, 0, 0, 0)
        program.map { it.split(" ").map { it.toInt() } }.forEach { (opcode, a, b, c) ->
            registry[c] = instructionsByOpcode[opcode]!!(a, b, registry)
        }
        return registry[0]
    }
}