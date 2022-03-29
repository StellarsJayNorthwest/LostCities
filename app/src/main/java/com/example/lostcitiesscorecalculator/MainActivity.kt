package com.example.lostcitiesscorecalculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lostcitiesscorecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var player1HandButtons: Array<Button>

    private lateinit var player2HandButtons: Array<Button>

    // This function totals up all of the buttons for one player, and then sets the total TextView
    // for that player to contain the total score and returns the total score.
    private fun computePlayerScore(player1: Boolean): Int {
        var playerTotal = 0
        for (button in (if (player1) this.player1HandButtons else this.player2HandButtons)) {
            playerTotal += button.text.toString().toInt()
        }

        (if (player1) binding.player1Total else binding.player2Total).setText(playerTotal.toString())

        return playerTotal
    }

    private val getHandScoringActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val player1Score = it.data?.getIntExtra("player1Score", 0)!!
                val player2Score = it.data?.getIntExtra("player2Score", 0)!!
                val hand = it.data?.getIntExtra("hand", 0)!!

                player1HandButtons[hand].setText(player1Score.toString())
                player2HandButtons[hand].setText(player2Score.toString())

                val player1Total = computePlayerScore(true)
                val player2Total = computePlayerScore(false)

                var message = ""
                if (player1Total == player2Total) {
                    message = "Tie game!"
                } else {
                    var winner =
                        if (player1Total > player2Total) binding.player1Name.text else binding.player2Name.text

                    if (hand < 2) {
                        message = "$winner is in the lead!"
                    } else {
                        message = "$winner is victorious!"
                    }
                }

                binding.finalResult.setText(message)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        this.player1HandButtons = arrayOf(
            binding.player1hand1,
            binding.player1hand2,
            binding.player1hand3
        )

        this.player2HandButtons = arrayOf(
            binding.player2hand1,
            binding.player2hand2,
            binding.player2hand3
        )

        for (playerIndex in 1..2) {
            for (handIndex in 0 until this.player1HandButtons.size) {
                val button =
                    (if (playerIndex == 1) this.player1HandButtons else this.player2HandButtons)[handIndex]

                button.setOnClickListener {
                    val intent = Intent(this, HandScoringActivity::class.java)
                    intent.putExtra("player1Name", binding.player1Name.text)
                    intent.putExtra("player2Name", binding.player2Name.text)
                    intent.putExtra("hand", handIndex + 1)
                    intent.putExtra("buttonId", button.id)
                    getHandScoringActivityResult.launch(intent)
                }
            }
        }

        setContentView(binding.root)
    }
}