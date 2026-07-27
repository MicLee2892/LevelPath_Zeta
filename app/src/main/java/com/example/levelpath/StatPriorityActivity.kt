package com.example.levelpath

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

//Michael Lee
//LevelPath - Screen 4: Set Your Priorities
//July 26, 2026
class StatPriorityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat_priority)

        val priorityContainer = findViewById<LinearLayout>(R.id.container_priorities)
        val desiredContainer = findViewById<LinearLayout>(R.id.container_desired_values)

        // One priority row and one desired-value row per stat, built in the
        // same order BuildSession.stats is in - that matching order is what
        // lets Generate Plan read the values back correctly by position.
        for (stat in BuildSession.stats) {
            buildPriorityRow(stat, priorityContainer)
            buildDesiredValueRow(stat, desiredContainer)
        }

        findViewById<TextView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<AppCompatButton>(R.id.btn_generate_plan).setOnClickListener {
            saveDesiredValues(desiredContainer)
            BuildSession.generatePlan()
            startActivity(Intent(this, GeneratedPlanActivity::class.java))
        }
    }

    private fun buildPriorityRow(stat: StatEntry, parent: LinearLayout) {
        val nameLabel = TextView(this)
        nameLabel.text = stat.name
        nameLabel.setTextColor(ContextCompat.getColor(this, R.color.lp_text_primary))
        nameLabel.textSize = 14f
        nameLabel.setTypeface(nameLabel.typeface, Typeface.BOLD)
        parent.addView(nameLabel)

        val chipRow = LinearLayout(this)
        chipRow.orientation = LinearLayout.HORIZONTAL
        val chipRowParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        chipRowParams.topMargin = dpToPx(8)
        chipRowParams.bottomMargin = dpToPx(18)
        chipRow.layoutParams = chipRowParams

        val labels = listOf("Primary", "Secondary", "Tertiary")
        val chips = mutableListOf<AppCompatButton>()

        for (index in labels.indices) {
            val label = labels[index]
            val chip = AppCompatButton(this)
            chip.text = label
            chip.textSize = 11f
            chip.setTypeface(chip.typeface, Typeface.BOLD)

            val chipParams = LinearLayout.LayoutParams(0, dpToPx(48))
            chipParams.weight = 1f
            if (index < labels.size - 1) {
                chipParams.rightMargin = dpToPx(6)
            }
            chip.layoutParams = chipParams

            chip.setOnClickListener {
                stat.priority = label
                updateChipStyles(chips, label)
            }

            chips.add(chip)
            chipRow.addView(chip)
        }

        // Reflect the stat's current priority (defaults to "Secondary")
        updateChipStyles(chips, stat.priority)
        parent.addView(chipRow)
    }

    private fun updateChipStyles(chips: List<AppCompatButton>, selectedLabel: String) {
        for (chip in chips) {
            if (chip.text.toString() == selectedLabel) {
                chip.setBackgroundResource(R.drawable.bg_chip_selected)
                chip.setTextColor(ContextCompat.getColor(this, R.color.white))
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_unselected)
                chip.setTextColor(ContextCompat.getColor(this, R.color.lp_text_secondary))
            }
        }
    }

    private fun buildDesiredValueRow(stat: StatEntry, parent: LinearLayout) {
        val row = LinearLayout(this)
        row.orientation = LinearLayout.VERTICAL
        val rowParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        rowParams.bottomMargin = dpToPx(14)
        row.layoutParams = rowParams

        val label = TextView(this)
        label.text = stat.name
        label.setTextColor(ContextCompat.getColor(this, R.color.lp_text_secondary))
        label.textSize = 11f
        row.addView(label)

        val input = EditText(this)
        input.hint = "e.g. 50"
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.setBackgroundResource(R.drawable.bg_card_row)
        input.setTextColor(ContextCompat.getColor(this, R.color.lp_text_primary))
        input.setHintTextColor(ContextCompat.getColor(this, R.color.lp_text_secondary))
        input.setPadding(dpToPx(12), 0, dpToPx(12), 0)
        val inputParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dpToPx(46)
        )
        inputParams.topMargin = dpToPx(4)
        input.layoutParams = inputParams
        row.addView(input)

        parent.addView(row)
    }

    private fun saveDesiredValues(desiredContainer: LinearLayout) {
        for (i in 0 until desiredContainer.childCount) {
            val row = desiredContainer.getChildAt(i) as LinearLayout
            val input = row.getChildAt(1) as EditText
            val value = input.text.toString().trim().toIntOrNull() ?: 0
            BuildSession.stats[i].desiredValue = value
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}