package com.example.levelpath

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

//Michael Lee
//LevelPath - Screen 3: Generic Mode - Define Your Game
//July 26, 2026
class GenericSetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic_setup)

        findViewById<TextView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<AppCompatButton>(R.id.btn_add_stat).setOnClickListener {
            addStatRow()
        }

        findViewById<AppCompatButton>(R.id.btn_next_priorities).setOnClickListener {
            saveFormAndContinue()
        }
    }

    private fun addStatRow() {
        val container = findViewById<LinearLayout>(R.id.container_stats)

        val row = LinearLayout(this)
        row.orientation = LinearLayout.VERTICAL
        row.setBackgroundResource(R.drawable.bg_card_row)
        row.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12))
        val rowParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        rowParams.bottomMargin = dpToPx(10)
        row.layoutParams = rowParams

        val nameInput = EditText(this)
        nameInput.hint = "New Stat"
        nameInput.setTextColor(ContextCompat.getColor(this, R.color.lp_text_primary))
        nameInput.setHintTextColor(ContextCompat.getColor(this, R.color.lp_text_secondary))
        nameInput.textSize = 14f
        nameInput.setTypeface(nameInput.typeface, Typeface.BOLD)
        nameInput.setPadding(0, 0, 0, 0)
        nameInput.setBackgroundColor(Color.TRANSPARENT)
        row.addView(nameInput)

        val costLabel = TextView(this)
        costLabel.text = getString(R.string.stat_point_cost)
        costLabel.setTextColor(ContextCompat.getColor(this, R.color.lp_text_secondary))
        costLabel.textSize = 11f
        val costParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        costParams.topMargin = dpToPx(2)
        costLabel.layoutParams = costParams
        row.addView(costLabel)

        container.addView(row)
    }

    private fun saveFormAndContinue() {
        val gameName = findViewById<EditText>(R.id.input_game_name).text.toString().trim()
        val expText = findViewById<EditText>(R.id.input_exp_per_level).text.toString().trim()
        val expPerLevel = expText.toIntOrNull() ?: 0

        val container = findViewById<LinearLayout>(R.id.container_stats)
        val statNames = mutableListOf<String>()
        for (i in 0 until container.childCount) {
            val row = container.getChildAt(i) as LinearLayout
            val nameView = row.getChildAt(0) as TextView
            val name = nameView.text.toString().trim()
            if (name.isNotEmpty()) {
                statNames.add(name)
            }
        }

        BuildSession.gameSystemName = if (gameName.isNotEmpty()) gameName else "Custom Game"
        BuildSession.expPerLevel = expPerLevel
        BuildSession.stats = statNames.map { StatEntry(name = it) }.toMutableList()

        startActivity(Intent(this, StatPriorityActivity::class.java))
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}