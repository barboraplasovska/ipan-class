package com.example.gameinfoapp.network

import com.example.gameinfoapp.models.Game
import retrofit2.http.GET

// GameService.kt
interface GameService {
    @GET("game/list.json")
    suspend fun getGameList(): List<Game>
}
