package com.example.gameinfoapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gameinfoapp.R
import com.example.gameinfoapp.models.Game
import com.example.gameinfoapp.network.ApiClient
import com.example.gameinfoapp.ui.adapters.GameListAdapter
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// MainActivity.kt
class MainActivity : AppCompatActivity(), GameListAdapter.OnItemClickListener {

    private lateinit var gameListAdapter: GameListAdapter

    private lateinit var games: List<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView and adapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        gameListAdapter = GameListAdapter(this) // Pass `this` as the listener
        recyclerView.adapter = gameListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // Fetch game list from API
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val gameList = ApiClient.gameService.getGameList()

                // Shuffle the list of games
                val shuffledGameList = gameList.shuffled()

                // Pick the first 4 games from the shuffled list
                games = shuffledGameList.take(4)

                withContext(Dispatchers.Main) {
                    gameListAdapter.updateList(games)
                }
            } catch (e: Exception) {
                // Handle error
                Log.e("MainActivity", "Error fetching game list", e)
            }
        }
    }


    override fun onItemClick(position: Int) {
        val gameId = games[position].id
        val intent = Intent(this@MainActivity, GameDetailActivity::class.java)
        intent.putExtra("gameId", gameId)
        startActivity(intent)
    }

}

