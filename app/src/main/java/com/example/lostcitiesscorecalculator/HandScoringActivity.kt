package com.example.lostcitiesscorecalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostcitiesscorecalculator.databinding.HandScoringActivityBinding

data class SingleColorScoring(val hand: Int, val playerName: String, val color: String) {
}

class HandScoringActivity : AppCompatActivity() {
    private lateinit var binding: HandScoringActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HandScoringActivityBinding.inflate(layoutInflater)

        val player1Name = intent.getStringExtra("player1Name") ?: "Player 1"
        val player2Name = intent.getStringExtra("player2Name") ?: "Player 2"
        val hand = intent.getIntExtra("hand", 0)
        assert(hand != 0)

        binding.handTitle.setText("Scoring for hand $hand")

        val singleScoringButtons = mapOf(
            binding.player1Red to SingleColorScoring(hand, player1Name,"red"),
            binding.player1Green to SingleColorScoring(hand, player1Name,"green"),
            binding.player1Blue to SingleColorScoring(hand, player1Name,"blue"),
            binding.player1White to SingleColorScoring(hand, player1Name,"white"),
            binding.player1Yellow to SingleColorScoring(hand, player1Name,"yellow"),
            binding.player1Purple to SingleColorScoring(hand, player1Name,"purple"),
            binding.player2Red to SingleColorScoring(hand, player2Name,"red"),
            binding.player2Green to SingleColorScoring(hand, player2Name,"green"),
            binding.player2Blue to SingleColorScoring(hand, player2Name,"blue"),
            binding.player2White to SingleColorScoring(hand, player2Name,"white"),
            binding.player2Yellow to SingleColorScoring(hand, player2Name,"yellow"),
            binding.player2Purple to SingleColorScoring(hand, player2Name,"purple")
        )

        for(b in singleScoringButtons) {
            b.key.setOnClickListener {
                val intent = Intent(this, SingleColorScoringActivity::class.java)
                intent.putExtra("hand", b.value.hand)
                intent.putExtra("playerName", b.value.playerName)
                intent.putExtra("color", b.value.color)
                startActivity(intent)
            }
        }

        setContentView(binding.root)
    }
}