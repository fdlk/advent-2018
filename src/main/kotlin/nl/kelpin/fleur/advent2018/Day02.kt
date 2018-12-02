package nl.kelpin.fleur.advent2018

data class TwosAndThrees(val twos: Int = 0, val threes: Int = 0) {
    constructor(id: String) : this(id.toCharArray().groupBy { it }.mapValues { it.value.size })
    constructor(occurrences: Map<Char, Int>) : this(
            if (occurrences.values.find { it == 2 } != null) 1 else 0,
            if (occurrences.values.find { it == 3 } != null) 1 else 0
    )

    fun combinedWith(other: TwosAndThrees): TwosAndThrees = TwosAndThrees(
            this.twos + other.twos,
            this.threes + other.threes
    )

    fun checksum(): Int = twos * threes
}

class Day02(private val input: List<String>) {
    fun part1() = input
            .map(::TwosAndThrees)
            .reduce { soFar, other -> soFar.combinedWith(other) }
            .checksum()

    fun part2(): String? = input.flatMap { a ->
        input.map { b -> b.matchingChars(a) }
                .filter { it.length == a.length - 1 }
    }.first()
}
