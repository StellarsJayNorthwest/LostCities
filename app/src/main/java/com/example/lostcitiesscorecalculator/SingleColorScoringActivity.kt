package com.example.lostcitiesscorecalculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostcitiesscorecalculator.databinding.SingleColorScoringActivityBinding

class SingleColorScoringActivity : AppCompatActivity() {
    private lateinit var binding: SingleColorScoringActivityBinding

    private var totalScore = 0

    private var buttonId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SingleColorScoringActivityBinding.inflate(layoutInflater)

        val color = intent.getStringExtra("color") ?: "BadColor"
        val playerName = intent.getStringExtra("playerName") ?: "BadPlayerName"
        this.buttonId = intent.getIntExtra("buttonId", 0)
        binding.headerText.setText("Tap each of the $color cards for $playerName")

        val wagerButtons = arrayOf(
            binding.wager1,
            binding.wager2,
            binding.wager3
        )

        val cardButtons = arrayOf(
            binding.card2,
            binding.card3,
            binding.card4,
            binding.card5,
            binding.card6,
            binding.card7,
            binding.card8,
            binding.card9,
            binding.card10
        )

        for (b in wagerButtons + cardButtons) {
            b.setOnClickListener {
                var cardSum = 0
                var wagerCount = 0
                var cardCount = 0

                for (bb in wagerButtons) {
                    if (bb.isChecked) {
                        ++wagerCount
                        ++cardCount
                    }
                }

                for (bb in cardButtons) {
                    if (bb.isChecked) {
                        cardSum += bb.text.toString().toInt()
                        ++cardCount
                    }
                }

                var newTotalScore = (wagerCount + 1) * (cardSum - 20)
                if (cardCount >= 8) {
                    newTotalScore += 20
                }

                var totalText = "$playerName: your score for $color is $newTotalScore"
                if (cardCount >= 8) {
                    totalText += " including 20 bonus points for playing $cardCount cards"
                }
                totalText += "!"

                binding.runningTotal.setText(totalText)
                this.totalScore = newTotalScore
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
        intent.putExtra("score", this.totalScore)
        intent.putExtra("buttonId", this.buttonId)
        setResult(Activity.RESULT_OK, intent)
    }
}