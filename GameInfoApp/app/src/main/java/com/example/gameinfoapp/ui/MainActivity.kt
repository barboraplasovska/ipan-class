package com.example.gameinfoapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gameinfoapp.R
import com.example.gameinfoapp.network.ApiClient
import com.example.gameinfoapp.ui.adapters.GameListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// MainActivity.kt
class MainActivity : AppCompatActivity(), GameListAdapter.OnItemClickListener {

    private lateinit var gameListAdapter: GameListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView and adapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        gameListAdapter = GameListAdapter(this) // Pass `this` as the listener
        recyclerView.adapter = gameListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch game list from API
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val gameList = ApiClient.gameService.getGameList()
                withContext(Dispatchers.Main) {
                    gameListAdapter.updateList(gameList)
                }
            } catch (e: Exception) {
                // Handle error
                Log.e("MainActivity", "Error fetching game list", e)
            }
        }
    }

    override fun onItemClick(position: Int) {
        // Handle item click event
    }
}

