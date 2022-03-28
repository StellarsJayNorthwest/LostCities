package com.example.lostcitiesscorecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostcitiesscorecalculator.databinding.SingleColorScoringActivityBinding

class SingleColorScoringActivity : AppCompatActivity() {
    private lateinit var binding: SingleColorScoringActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SingleColorScoringActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}