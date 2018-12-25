package nl.kelpin.fleur.advent2018

class Day24(immune: List<String>, infection: List<String>) {
    companion object {
        const val INFECTION = "infection"
        const val IMMUNE = "immune"
        const val WEAK = "weak"
        val highestInitiative = compareByDescending(Group::initiative)
        val mostEffectivePowerThenHighestInitiative = compareByDescending(Group::effectivePower)
                .then(highestInitiative)
    }

    data class Susceptibility(val weakOrImmune: String, val types: List<String>) {
        companion object {
            fun parse(string: String): List<Susceptibility> {
                return string.split("; ").filterNot(String::isBlank)
                        .map { it ->
                            val (weakOrImmune, typesString) = it
                                    .split("to")
                                    .map(String::trim)
                            val types = typesString.split(", ")
                            Susceptibility(weakOrImmune, types.map(String::trim))
                        }
            }
        }
    }

    data class Group(val side: String,
                     val index: Int,
                     val count: Int,
                     val hpEach: Int,
                     val susceptibilities: List<Susceptibility>,
                     val damage: Int,
                     val damageType: String,
                     val initiative: Int) {
        companion object {
            private val groupRE = Regex("""(\d+) units each with (\d+) hit points (?>\((.+)\))? ?with an attack that does (\d+) (\w+) damage at initiative (\d+)""")

            fun parse(side: String, line: String, index: Int): Group {
                val match = groupRE.matchEntire(line)
                val (count, hpEach, susceptibilitiesString, damage, damageType, initiative) = match!!.destructured
                val susceptibilities = Susceptibility.parse(susceptibilitiesString)
                return Group(side, index, count.toInt(), hpEach.toInt(), susceptibilities, damage.toInt(), damageType, initiative.toInt())
            }
        }

        fun getId(): String = "$side #$index"

        override fun toString(): String = "${getId()} ($count left)"

        fun effectivePower(): Int = count * damage
        private fun isWeakTo(damageType: String): Boolean = susceptibilities.any { susceptibility ->
            susceptibility.weakOrImmune == WEAK && susceptibility.types.contains(damageType)
        }

        private fun isImmuneTo(damageType: String): Boolean = susceptibilities.any { susceptibility ->
            susceptibility.weakOrImmune == IMMUNE && susceptibility.types.contains(damageType)
        }

        fun attackedBy(other: Group): Group? {
            val factor = when {
                isImmuneTo(other.damageType) -> 0
                isWeakTo(other.damageType) -> 2
                else -> 1
            }
            val unitsLeft = count - (other.effectivePower() * factor) / hpEach
            return when {
                unitsLeft > 0 -> copy(count = unitsLeft)
                else -> null
            }
        }

        fun boosted(boost: Int) = copy(damage = damage + boost)

        fun findTarget(targets: List<Group>): Group? =
                targets.find { target -> target.isWeakTo(damageType) }
                        ?: targets.find { target -> !target.isImmuneTo(damageType) }
    }

    val groups = immune.map { Group.parse(IMMUNE, it, immune.indexOf(it) + 1) } +
            infection.map { Group.parse(INFECTION, it, infection.indexOf(it) + 1) }

    data class State(val groups: List<Group>) {

        fun targetSelection(): Map<String, Group?> {
            val attackers = groups.sortedWith(mostEffectivePowerThenHighestInitiative).toMutableList()
            val targets = attackers.toMutableList()

            return attackers.associateBy(Group::getId) { attacker ->
                val target = attacker.findTarget(targets.filter { it.side != attacker.side })
                if (target != null) {
                    targets.remove(target)
                }
                target
            }
        }

        fun done(): Boolean = groups.map { it.side }.distinct().size == 1

        fun next(): State {
            val targetSelection = targetSelection()
            val attackers = groups.sortedWith(highestInitiative).toMutableList()
            val result = groups.toMutableList()
            while (!attackers.isEmpty()) {
                val attacker = attackers.removeAt(0)
                val target = targetSelection[attacker.getId()] ?: continue
                val attackedTarget = target.attackedBy(attacker)
                result.update(target, attackedTarget)
                attackers.update(target, attackedTarget)
            }
            return State(result)
        }

        fun withImmuneBoost(boost: Int): State =
                copy(groups = groups.map { if (it.side == IMMUNE) it.boosted(boost) else it })

        fun count(): Int = groups.sumBy { it.count }
    }

    private tailrec fun battle(state: State): State =
            if (state.done()) state
            else {
                val next = state.next()
                if (next == state) next // standoff!
                else battle(next)
            }

    fun part1(state: State = State(groups)): Int = battle(state).count()

    tailrec fun part2(boost: Int): Int {
        println("boost = $boost")
        val result = battle(State(groups).withImmuneBoost(boost))
        return if (result.groups.none { it.side == INFECTION }) result.count()
        else part2(boost + 1)
    }
}