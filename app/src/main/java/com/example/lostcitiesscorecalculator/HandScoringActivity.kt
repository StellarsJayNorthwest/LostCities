package com.example.lostcitiesscorecalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostcitiesscorecalculator.databinding.HandScoringActivityBinding

class HandScoringActivity : AppCompatActivity() {
    private lateinit var binding: HandScoringActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HandScoringActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.player1Blue.setOnClickListener {
            val intent = Intent(this, SingleColorScoringActivity::class.java)
            startActivity(intent)
        }
    }
}