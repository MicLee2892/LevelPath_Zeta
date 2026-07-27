package com.example.levelpath

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

//Michael Lee
//LevelPath - Screen 2: Preset Mode - Select Your Game
//July 26, 2026
class PresetGameSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preset_game_select)

        findViewById<TextView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        findViewById<LinearLayout>(R.id.row_game_ashfall).setOnClickListener {
            loadPresetAndContinue(
                gameName = "Ashfall Online",
                expPerLevel = 300,
                statNames = listOf("Damage", "Healing", "Defense")
            )
        }

        findViewById<LinearLayout>(R.id.row_game_chrono).setOnClickListener {
            loadPresetAndContinue(
                gameName = "Chrono Blades",
                expPerLevel = 150,
                statNames = listOf("Damage", "Speed", "Defense")
            )
        }

        findViewById<LinearLayout>(R.id.row_game_ironhold).setOnClickListener {
            loadPresetAndContinue(
                gameName = "Ironhold Legends",
                expPerLevel = 250,
                statNames = listOf("Damage", "Persuasion", "Defense")
            )
        }

        findViewById<LinearLayout>(R.id.row_game_starforge).setOnClickListener {
            loadPresetAndContinue(
                gameName = "Starforge Tactics",
                expPerLevel = 400,
                statNames = listOf("Damage", "Command", "Defense")
            )
        }

        findViewById<LinearLayout>(R.id.row_game_wyrmreach).setOnClickListener {
            loadPresetAndContinue(
                gameName = "Wyrmreach",
                expPerLevel = 350,
                statNames = listOf("Damage", "Magic", "Defense")
            )
        }
    }

    private fun loadPresetAndContinue(gameName: String, expPerLevel: Int, statNames: List<String>) {
        BuildSession.gameSystemName = gameName
        BuildSession.expPerLevel = expPerLevel
        BuildSession.stats = statNames.map { StatEntry(name = it) }.toMutableList()
        startActivity(Intent(this, StatPriorityActivity::class.java))
    }
}