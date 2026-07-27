package com.example.levelpath

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

//Michael Lee
//LevelPath - Screen 5: Your Leveling Plan
//July 26, 2026
class GeneratedPlanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generated_plan)

        val container = findViewById<LinearLayout>(R.id.container_plan_levels)
        for (level in BuildSession.plan) {
            buildLevelRow(level, container)
        }

        findViewById<TextView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<AppCompatButton>(R.id.btn_start_tracking).setOnClickListener {
            val savedBuild = SavedBuild(
                buildName = generateBuildName(),
                sourceLabel = BuildSession.gameSystemName,
                stats = BuildSession.stats.toList(),
                plan = BuildSession.plan.toList()
            )
            SavedBuildsRepository.builds.add(savedBuild)
            SavedBuildsRepository.currentlyViewedIndex = SavedBuildsRepository.builds.size - 1
            startActivity(Intent(this, ProgressTrackerActivity::class.java))
        }
    }

    private fun buildLevelRow(level: PlanLevel, parent: LinearLayout) {
        val row = LinearLayout(this)
        row.orientation = LinearLayout.HORIZONTAL
        row.gravity = Gravity.CENTER_VERTICAL
        row.setBackgroundResource(R.drawable.bg_card_row)
        row.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12))
        val rowParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        rowParams.bottomMargin = dpToPx(10)
        row.layoutParams = rowParams

        val badge = TextView(this)
        badge.text = level.levelNumber.toString()
        badge.setBackgroundResource(R.drawable.bg_small_circle)
        badge.setTextColor(ContextCompat.getColor(this, R.color.lp_primary))
        badge.textSize = 13f
        badge.setTypeface(badge.typeface, Typeface.BOLD)
        badge.gravity = Gravity.CENTER
        badge.layoutParams = LinearLayout.LayoutParams(dpToPx(34), dpToPx(34))
        row.addView(badge)

        val textBlock = LinearLayout(this)
        textBlock.orientation = LinearLayout.VERTICAL
        val textBlockParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textBlockParams.leftMargin = dpToPx(12)
        textBlock.layoutParams = textBlockParams

        val allocateLabel = TextView(this)
        allocateLabel.text = getString(R.string.plan_allocate_label)
        allocateLabel.setTextColor(ContextCompat.getColor(this, R.color.lp_text_secondary))
        allocateLabel.textSize = 10f
        textBlock.addView(allocateLabel)

        val statLabel = TextView(this)
        statLabel.text = level.statName
        statLabel.setTextColor(ContextCompat.getColor(this, R.color.lp_text_primary))
        statLabel.textSize = 14f
        statLabel.setTypeface(statLabel.typeface, Typeface.BOLD)
        textBlock.addView(statLabel)

        row.addView(textBlock)
        parent.addView(row)
    }

    private fun generateBuildName(): String {
        val primaryStats = BuildSession.stats.filter { it.priority == "Primary" }.map { it.name }
        return when {
            primaryStats.size >= 2 -> "${primaryStats[0]} / ${primaryStats[1]} Build"
            primaryStats.size == 1 -> "${primaryStats[0]} Build"
            else -> "${BuildSession.gameSystemName} Build"
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}