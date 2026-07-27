package com.example.levelpath

//Michael Lee
//LevelPath - Shared Build State
//July 26, 2026

object BuildSession {

    var gameSystemName: String = ""
    var expPerLevel: Int = 0
    var stats: MutableList<StatEntry> = mutableListOf()
    var plan: MutableList<PlanLevel> = mutableListOf()

    fun reset() {
        gameSystemName = ""
        expPerLevel = 0
        stats = mutableListOf()
        plan = mutableListOf()
    }
    fun generatePlan(): MutableList<PlanLevel> {
        val weight = mapOf("Primary" to 3, "Secondary" to 2, "Tertiary" to 1)

        val targets = mutableMapOf<String, Int>()
        val allocated = mutableMapOf<String, Int>()
        for (stat in stats) {
            targets[stat.name] = stat.desiredValue
            allocated[stat.name] = 0
        }

        val totalLevels = targets.values.sum()
        val newPlan = mutableListOf<PlanLevel>()

        for (levelNumber in 1..totalLevels) {
            var chosenStat: StatEntry? = null
            var lowestRatio = Double.MAX_VALUE

            for (stat in stats) {
                val target = targets[stat.name] ?: 0
                val allocatedSoFar = allocated[stat.name] ?: 0
                if (allocatedSoFar < target) {
                    val statWeight = weight[stat.priority] ?: 1
                    val ratio = allocatedSoFar.toDouble() / statWeight
                    if (ratio < lowestRatio) {
                        lowestRatio = ratio
                        chosenStat = stat
                    }
                }
            }

            if (chosenStat != null) {
                newPlan.add(PlanLevel(levelNumber, chosenStat.name))
                allocated[chosenStat.name] = (allocated[chosenStat.name] ?: 0) + 1
            }
        }

        plan = newPlan
        return newPlan
    }
}

object SavedBuildsRepository {
    val builds: MutableList<SavedBuild> = mutableListOf()
    var currentlyViewedIndex: Int = -1
}