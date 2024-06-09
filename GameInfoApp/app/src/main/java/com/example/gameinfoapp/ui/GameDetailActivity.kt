package com.example.gameinfoapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gameinfoapp.R
import com.example.gameinfoapp.models.Game

class GameDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        val game = intent.getSerializableExtra("game") as Game

        // Set up views and load game details
        // Example:
        val imageView: ImageView = findViewById(R.id.gameImage)
        val nameTextView: TextView = findViewById(R.id.gameName)
        val typeTextView: TextView = findViewById(R.id.gameType)
        // Bind other views and load details

        nameTextView.text = game.name
        typeTextView.text = game.type

        Glide.with(this)
            .load(game.picture)
            .into(imageView)

        // Set up button click listener
        val button: Button = findViewById(R.id.knowMoreButton)
        button.setOnClickListener {
            // Handle button click, open browser to game URL or Google search
        }
    }
}
