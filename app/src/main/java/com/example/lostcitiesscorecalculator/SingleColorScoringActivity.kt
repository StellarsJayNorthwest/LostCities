package com.example.lostcitiesscorecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostcitiesscorecalculator.databinding.SingleColorScoringActivityBinding

class SingleColorScoringActivity : AppCompatActivity() {
    private lateinit var binding: SingleColorScoringActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SingleColorScoringActivityBinding.inflate(layoutInflater)
        binding.headerText.setText("Tap each of the cards TODO fill in player name and color")
        setContentView(binding.root)
    }
}