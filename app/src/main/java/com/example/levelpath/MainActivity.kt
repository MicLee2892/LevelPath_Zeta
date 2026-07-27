package com.example.levelpath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

//Michael Lee
//LevelPath - Screen 1: Home / Mode Selection
//July 26, 2026
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPresetMode = findViewById<Button>(R.id.btn_preset_mode)
        val btnGenericMode = findViewById<Button>(R.id.btn_generic_mode)
        val rowMyBuilds = findViewById<LinearLayout>(R.id.row_my_builds)

        btnPresetMode.setOnClickListener {
            // Starting a new build wipes any in-progress build so Preset
            // Mode always starts from a clean slate.
            BuildSession.reset()
            startActivity(Intent(this, PresetGameSelectActivity::class.java))
        }

        btnGenericMode.setOnClickListener {
            BuildSession.reset()
            startActivity(Intent(this, GenericSetupActivity::class.java))
        }

        rowMyBuilds.setOnClickListener {
            startActivity(Intent(this, MyBuildsActivity::class.java))
        }
    }
}