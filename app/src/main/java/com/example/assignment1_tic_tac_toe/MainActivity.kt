package com.example.assignment1_tic_tac_toe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private lateinit var statusTextView: TextView

    private var isPlayerXTurn = true
    private var moveCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)

        val buttonIds = arrayOf(
            arrayOf(R.id.button_00, R.id.button_01, R.id.button_02),
            arrayOf(R.id.button_10, R.id.button_11, R.id.button_12),
            arrayOf(R.id.button_20, R.id.button_21, R.id.button_22)
        )

        buttons = Array(3) { row ->
            Array(3) { col ->
                findViewById<Button>(buttonIds[row][col]).apply {
                    setOnClickListener { button -> onButtonClick(button as Button, row, col) }
                }
            }
        }

        findViewById<Button>(R.id.playAgainButton).setOnClickListener {
            resetGame()
        }
    }

    private fun onButtonClick(button: Button, row: Int, col: Int) {
        if (button.text.isNotEmpty()) return

        if (isPlayerXTurn) {
            button.text = "X"
            button.setTextColor(resources.getColor(android.R.color.holo_red_dark, theme)) // צבע אדום ל-X
        } else {
            button.text = "O"
            button.setTextColor(resources.getColor(android.R.color.holo_blue_dark, theme)) // צבע כחול ל-O
        }
        moveCount++

        if (checkWin()) {
            statusTextView.text = if (isPlayerXTurn) "Player X Wins!" else "Player O Wins!"
            disableButtons()
        } else if (moveCount == 9) {
            statusTextView.text = "It's a Draw!"
        } else {
            isPlayerXTurn = !isPlayerXTurn
            statusTextView.text = if (isPlayerXTurn) "Player X's Turn" else "Player O's Turn"
        }
    }

    private fun checkWin(): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if (buttons[i][0].text.isNotEmpty() &&
                buttons[i][0].text == buttons[i][1].text &&
                buttons[i][0].text == buttons[i][2].text
            ) return true

            if (buttons[0][i].text.isNotEmpty() &&
                buttons[0][i].text == buttons[1][i].text &&
                buttons[0][i].text == buttons[2][i].text
            ) return true
        }

        // Check diagonals
        if (buttons[0][0].text.isNotEmpty() &&
            buttons[0][0].text == buttons[1][1].text &&
            buttons[0][0].text == buttons[2][2].text
        ) return true

        if (buttons[0][2].text.isNotEmpty() &&
            buttons[0][2].text == buttons[1][1].text &&
            buttons[0][2].text == buttons[2][0].text
        ) return true

        return false
    }

    private fun disableButtons() {
        for (row in buttons) {
            for (button in row) {
                button.isEnabled = false
            }
        }
    }

    private fun resetGame() {
        for (row in buttons) {
            for (button in row) {
                button.text = ""
                button.isEnabled = true
            }
        }
        isPlayerXTurn = true
        moveCount = 0
        statusTextView.text = "Player X's Turn"
    }
}
