package com.example.levelpath

import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

//Michael Lee
//LevelPath - Screen 6: Progress Tracker
//July 26, 2026
class ProgressTrackerActivity : AppCompatActivity() {

    private lateinit var build: SavedBuild

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_tracker)
        build = SavedBuildsRepository.builds[SavedBuildsRepository.currentlyViewedIndex]

        findViewById<TextView>(R.id.text_build_name).text = build.buildName

        val container = findViewById<LinearLayout>(R.id.container_checklist)
        for (level in build.plan) {
            buildChecklistRow(level, container)
        }

        updateProgressDisplay()

        findViewById<TextView>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }

    private fun buildChecklistRow(level: PlanLevel, parent: LinearLayout) {
        val row = LinearLayout(this)
        row.orientation = LinearLayout.HORIZONTAL
        row.gravity = android.view.Gravity.CENTER_VERTICAL
        row.setBackgroundResource(R.drawable.bg_card_row)
        row.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10))
        val rowParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        rowParams.bottomMargin = dpToPx(10)
        row.layoutParams = rowParams

        val checkbox = CheckBox(this)
        checkbox.isChecked = build.completedLevels.contains(level.levelNumber)

        val textBlock = LinearLayout(this)
        textBlock.orientation = LinearLayout.VERTICAL
        val textBlockParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textBlockParams.leftMargin = dpToPx(8)
        textBlock.layoutParams = textBlockParams

        val levelLabel = TextView(this)
        levelLabel.text = "Level ${level.levelNumber}"
        levelLabel.setTextColor(ContextCompat.getColor(this, R.color.lp_text_secondary))
        levelLabel.textSize = 10f
        textBlock.addView(levelLabel)

        val statLabel = TextView(this)
        statLabel.text = level.statName
        statLabel.setTextColor(ContextCompat.getColor(this, R.color.lp_text_primary))
        statLabel.textSize = 13f
        statLabel.setTypeface(statLabel.typeface, android.graphics.Typeface.BOLD)
        textBlock.addView(statLabel)

        checkbox.setOnClickListener {
            if (checkbox.isChecked) {
                build.completedLevels.add(level.levelNumber)
            } else {
                build.completedLevels.remove(level.levelNumber)
            }
            updateProgressDisplay()
        }

        row.addView(checkbox)
        row.addView(textBlock)
        parent.addView(row)
    }

    private fun updateProgressDisplay() {
        val total = build.plan.size
        val completed = build.completedLevels.size
        val percent = if (total > 0) (completed * 100) / total else 0

        findViewById<ProgressBar>(R.id.progress_bar_levels).progress = percent
        findViewById<TextView>(R.id.text_progress_summary).text =
            "Level $completed of $total complete"
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}