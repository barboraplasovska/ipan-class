package com.example.gameinfoapp.network

import com.example.gameinfoapp.models.Game
import com.example.gameinfoapp.models.GameDetails
import retrofit2.http.GET
import retrofit2.http.Path

// GameService.kt
interface GameService {
    @GET("game/list.json")
    suspend fun getGameList(): List<Game>

    @GET("game/details{id}.json")
    suspend fun getGameDetails(@Path("id") id: Int): GameDetails // Assuming you have a model for game details

}
