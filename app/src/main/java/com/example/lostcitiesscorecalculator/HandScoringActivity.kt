package com.example.lostcitiesscorecalculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ToggleButton
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lostcitiesscorecalculator.databinding.HandScoringActivityBinding

// The SingleColorScoring class is used to set up a map of scoring buttons.
data class SingleColorScoring(val handIndex: Int, val playerName: String, val color: String) {
}

class HandScoringActivity : AppCompatActivity() {
    private lateinit var binding: HandScoringActivityBinding

    private lateinit var player1SingleScoringButtons: Map<Button, SingleColorScoring>

    private lateinit var player2SingleScoringButtons: Map<Button, SingleColorScoring>

    private var buttonId = 0

    private var handIndex = 0

    // This function totals up all of the buttons for one player, and then sets the total TextView
    // for that player to contain the total score and returns the total score.
    private fun computePlayerScore(player1: Boolean): Int {
        var playerTotal = 0
        for (button in (if (player1) player1SingleScoringButtons else player2SingleScoringButtons)) {
            playerTotal += button.key.text.toString().toInt()
        }

        (if (player1) binding.player1Total else binding.player2Total).setText(playerTotal.toString())

        return playerTotal
    }

    private val getSingleColorScoringActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                // Get the score and button ID from the returned intent.
                val score = it.data?.getIntExtra("score", 0)!!
                val buttonId = it.data?.getIntExtra("buttonId", 0)!!

                // Set the appropriate button's text to the score that was just computed.
                val b: Button = findViewById(buttonId)
                b.setText(score.toString())

                // Re-total the scores for both players.
                computePlayerScore(true)
                computePlayerScore(false)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HandScoringActivityBinding.inflate(layoutInflater)

        val player1Name = intent.getStringExtra("player1Name") ?: "Player 1"
        val player2Name = intent.getStringExtra("player2Name") ?: "Player 2"
        this.buttonId = intent.getIntExtra("buttonId", 0)
        assert(this.buttonId != 0)
        this.handIndex = intent.getIntExtra("handIndex", 0)
        assert(this.handIndex >= 0 && this.handIndex <= 2)

        binding.player1Name.setText(player1Name)
        binding.player2Name.setText(player2Name)
        binding.handTitle.setText("Scoring for hand ${handIndex + 1}")
        binding.handTotalsTitle.setText("Hand ${handIndex + 1} totals")

        this.player1SingleScoringButtons = mapOf(
            binding.player1Red to SingleColorScoring(this.handIndex, player1Name, "red"),
            binding.player1Green to SingleColorScoring(this.handIndex, player1Name, "green"),
            binding.player1Blue to SingleColorScoring(this.handIndex, player1Name, "blue"),
            binding.player1White to SingleColorScoring(this.handIndex, player1Name, "white"),
            binding.player1Yellow to SingleColorScoring(this.handIndex, player1Name, "yellow"),
            binding.player1Purple to SingleColorScoring(this.handIndex, player1Name, "purple"),
        )

        this.player2SingleScoringButtons = mapOf(
            binding.player2Red to SingleColorScoring(this.handIndex, player2Name, "red"),
            binding.player2Green to SingleColorScoring(this.handIndex, player2Name, "green"),
            binding.player2Blue to SingleColorScoring(this.handIndex, player2Name, "blue"),
            binding.player2White to SingleColorScoring(this.handIndex, player2Name, "white"),
            binding.player2Yellow to SingleColorScoring(this.handIndex, player2Name, "yellow"),
            binding.player2Purple to SingleColorScoring(this.handIndex, player2Name, "purple")
        )

        for (b in player1SingleScoringButtons + player2SingleScoringButtons) {
            b.key.setOnClickListener {
                val intent = Intent(this, SingleColorScoringActivity::class.java)
                intent.putExtra("handIndex", b.value.handIndex)
                intent.putExtra("playerName", b.value.playerName)
                intent.putExtra("color", b.value.color)
                intent.putExtra("buttonId", b.key.id)
                getSingleColorScoringActivityResult.launch(intent)
            }
        }

        binding.doneButton.setOnClickListener {
            setActivityResult()
            finish()
        }

        setContentView(binding.root)
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun setActivityResult() {
        val intent = Intent()
        intent.putExtra("player1Score", computePlayerScore(true))
        intent.putExtra("player2Score", computePlayerScore(false))
        intent.putExtra("handIndex", this.handIndex)
        setResult(Activity.RESULT_OK, intent)
    }
}