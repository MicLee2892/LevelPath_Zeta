package com.example.levelpath
//Michael Lee
//LevelPath - Data Models
//July 26, 2026
data class StatEntry(
    var name: String,
    var pointCost: Int = 1,
    var priority: String = "Secondary",
    var desiredValue: Int = 0
)
data class PlanLevel(
    val levelNumber: Int,
    val statName: String
)
data class SavedBuild(
    val buildName: String,
    val sourceLabel: String,
    val stats: List<StatEntry>,
    val plan: List<PlanLevel>,
    val completedLevels: MutableSet<Int> = mutableSetOf()
)