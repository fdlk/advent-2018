package nl.kelpin.fleur.advent2018

class Day19(val program: List<String>, val ipReg: Int) {
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
    }

    fun update(regs: List<Int>, indexToUpdate: Int, updatedValue: Int): List<Int> = regs.mapIndexed { index, value ->
        if (index == indexToUpdate) updatedValue
        else value
    }

    fun regses(initial: List<Int>) = sequence {
        var regs = initial
        while (program.indices.contains(regs[ipReg])) {
            val ip = regs[ipReg]
            yield(regs)
            val line = program[ip]
            val (instruction, aString, bString, cString) = line.split(" ")
            val result = when (instruction) {
                "addr" -> addr(aString.toInt(), bString.toInt(), regs)
                "addi" -> addi(aString.toInt(), bString.toInt(), regs)
                "mulr" -> mulr(aString.toInt(), bString.toInt(), regs)
                "muli" -> muli(aString.toInt(), bString.toInt(), regs)
                "banr" -> banr(aString.toInt(), bString.toInt(), regs)
                "bani" -> bani(aString.toInt(), bString.toInt(), regs)
                "borr" -> borr(aString.toInt(), bString.toInt(), regs)
                "bori" -> bori(aString.toInt(), bString.toInt(), regs)
                "setr" -> setr(aString.toInt(), bString.toInt(), regs)
                "seti" -> seti(aString.toInt(), bString.toInt(), regs)
                "gtir" -> gtir(aString.toInt(), bString.toInt(), regs)
                "gtri" -> gtri(aString.toInt(), bString.toInt(), regs)
                "gtrr" -> gtrr(aString.toInt(), bString.toInt(), regs)
                "eqir" -> eqir(aString.toInt(), bString.toInt(), regs)
                "eqri" -> eqri(aString.toInt(), bString.toInt(), regs)
                "eqrr" -> eqrr(aString.toInt(), bString.toInt(), regs)
                else -> throw IllegalArgumentException("Unknown opcode: '$instruction'")
            }
            regs = update(regs, cString.toInt(), result)
            regs = update(regs, ipReg, regs[ipReg] + 1)
        }
    }

    fun part1() = regses(listOf(0, 0, 0, 0, 0, 0)).last().get(0)
    fun part2() = (1..10551394).filter { 10551394 % it == 0 }.sum()
}