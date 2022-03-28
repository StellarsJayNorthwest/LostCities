package com.example.lostcitiesscorecalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostcitiesscorecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Create a map of the hand buttons to associate a hand argument value with each button.
        // Each button for one hand opens the same activity to allow scoring for that hand for
        // both players at once.
        val handButtons = mapOf(
            binding.player1hand1 to 1,
            binding.player1hand2 to 2,
            binding.player1hand3 to 3,
            binding.player2hand1 to 1,
            binding.player2hand2 to 2,
            binding.player2hand3 to 3,
        )

        for (b in handButtons) {
            b.key.setOnClickListener {
                val intent = Intent(this, HandScoringActivity::class.java)
                intent.putExtra("player1Name", binding.player1Name.text)
                intent.putExtra("player2Name", binding.player2Name.text)
                intent.putExtra("hand", b.value)
                startActivity(intent)
            }
        }

        setContentView(binding.root)
    }
}