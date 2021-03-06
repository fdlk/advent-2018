package nl.kelpin.fleur.advent2018

import kotlin.reflect.KFunction3

typealias Operation = KFunction3<Int, Int, List<Int>, Int>
typealias OpCode = Int

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

        fun isAmbiguous(): Boolean = operations.count(::matchesOperation) >= 3
        fun matchesOperation(operation: Operation) = operation(a, b, r) == output
    }

    private fun matchingOperations(samples: Collection<Sample>, operations: Collection<Operation>): List<Operation> =
            operations.filter { operation -> samples.all { it.matchesOperation(operation) } }

    val samples: List<Sample> = samples.windowed(3, 4).map(Sample.Companion::of)

    fun part1(): Int = samples.count(Sample::isAmbiguous)

    tailrec fun resolveMappings(soFar: Map<OpCode, Operation> = mapOf(),
                                unmatchedOperations: Set<Operation> = Day16.operations.toSet(),
                                unmatchedOpCodes: Set<OpCode> = samples.map { it.opcode }.toSet()): Map<OpCode, Operation> {
        val newlyMatched = samples
                .groupBy { it.opcode }
                .filterKeys { unmatchedOpCodes.contains(it) }
                .mapValues { opcodeSamples -> matchingOperations(opcodeSamples.value, unmatchedOperations) }
                .filterValues { it.size == 1 }
                .mapValues { it.value.first() }
        if (newlyMatched.isEmpty()) {
            return soFar
        }
        return resolveMappings(soFar + newlyMatched,
                unmatchedOperations - newlyMatched.values,
                unmatchedOpCodes - newlyMatched.keys)
    }

    fun part2(): Int {
        val instructionsByOpcode = resolveMappings()
        val instructions = program.map { line -> line.split(" ").map { it.toInt() } }
        val registry = mutableListOf(0, 0, 0, 0)
        instructions.forEach { (opcode, a, b, c) ->
            registry[c] = instructionsByOpcode[opcode]!!(a, b, registry)
        }
        return registry[0]
    }
}