package com.example.gameinfoapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gameinfoapp.R
import com.example.gameinfoapp.models.GameDetails
import com.example.gameinfoapp.network.ApiClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        // Retrieve the gameId passed from MainActivity
        val gameId = intent.getIntExtra("gameId", -1)

        // Check if gameId is valid
        if (gameId != -1) {
            // Call API to get game details using gameId
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val gameDetails = ApiClient.gameService.getGameDetails(gameId)
                    withContext(Dispatchers.Main) {
                        // Update UI with game details
                        updateUI(gameDetails)
                    }
                } catch (e: Exception) {
                    // Handle error
                    Log.e("GameDetailActivity", "Error fetching game details", e)
                }
            }
        } else {
            // Handle invalid gameId
            Log.e("GameDetailActivity", "Invalid gameId")
        }
    }

    private fun updateUI(gameDetails: GameDetails) {
        // Set up views and load game details
        val imageView: ImageView = findViewById(R.id.gameImage)
        val nameTextView: TextView = findViewById(R.id.gameName)
        val typeTextView: TextView = findViewById(R.id.gameType)
        val descriptionTextView: TextView = findViewById(R.id.gameDescription)

        // Bind other views and load details
        nameTextView.text = gameDetails.name
        typeTextView.text = gameDetails.type
        descriptionTextView.text = gameDetails.description_en

        Glide.with(this)
            .load(gameDetails.picture)
            .into(imageView)

        // Set up button click listener
        val button: Button = findViewById(R.id.knowMoreButton)
        button.setOnClickListener {
            val gameDetailsUrl = "https://www.surleweb.xyz/api/game/details${gameDetails.id}.json"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(gameDetailsUrl))
            startActivity(intent)
        }
    }

}
