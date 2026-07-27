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
//LevelPath - Screen 7: My Builds
//July 26, 2026
class MyBuildsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_builds)

        val container = findViewById<LinearLayout>(R.id.container_builds)

        if (SavedBuildsRepository.builds.isEmpty()) {
            val emptyLabel = TextView(this)
            emptyLabel.text = "No saved builds yet - start one from Home."
            emptyLabel.setTextColor(ContextCompat.getColor(this, R.color.lp_text_secondary))
            emptyLabel.textSize = 12f
            container.addView(emptyLabel)
        } else {
            for (index in SavedBuildsRepository.builds.indices) {
                buildBuildRow(SavedBuildsRepository.builds[index], index, container)
            }
        }

        findViewById<TextView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<AppCompatButton>(R.id.btn_new_build).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun buildBuildRow(build: SavedBuild, index: Int, parent: LinearLayout) {
        val row = LinearLayout(this)
        row.orientation = LinearLayout.HORIZONTAL
        row.gravity = Gravity.CENTER_VERTICAL
        row.setBackgroundResource(R.drawable.bg_card_row)
        row.setPadding(dpToPx(14), dpToPx(14), dpToPx(14), dpToPx(14))
        val rowParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        rowParams.bottomMargin = dpToPx(12)
        row.layoutParams = rowParams

        val icon = TextView(this)
        icon.setBackgroundResource(R.drawable.bg_small_circle)
        icon.layoutParams = LinearLayout.LayoutParams(dpToPx(40), dpToPx(40))
        row.addView(icon)

        val textBlock = LinearLayout(this)
        textBlock.orientation = LinearLayout.VERTICAL
        val textBlockParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        textBlockParams.weight = 1f
        textBlockParams.leftMargin = dpToPx(14)
        textBlock.layoutParams = textBlockParams

        val nameLabel = TextView(this)
        nameLabel.text = build.buildName
        nameLabel.setTextColor(ContextCompat.getColor(this, R.color.lp_text_primary))
        nameLabel.textSize = 15f
        nameLabel.setTypeface(nameLabel.typeface, Typeface.BOLD)
        textBlock.addView(nameLabel)

        val total = build.plan.size
        val completed = build.completedLevels.size
        val percent = if (total > 0) (completed * 100) / total else 0

        val subtitleLabel = TextView(this)
        subtitleLabel.text = "${build.sourceLabel} \u00B7 $percent% complete"
        subtitleLabel.setTextColor(ContextCompat.getColor(this, R.color.lp_text_secondary))
        subtitleLabel.textSize = 11f
        val subtitleParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        subtitleParams.topMargin = dpToPx(2)
        subtitleLabel.layoutParams = subtitleParams
        textBlock.addView(subtitleLabel)

        row.addView(textBlock)

        val chevron = TextView(this)
        chevron.text = ">"
        chevron.setTextColor(ContextCompat.getColor(this, R.color.lp_text_secondary))
        chevron.textSize = 16f
        row.addView(chevron)

        row.setOnClickListener {
            SavedBuildsRepository.currentlyViewedIndex = index
            startActivity(Intent(this, ProgressTrackerActivity::class.java))
        }

        parent.addView(row)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}