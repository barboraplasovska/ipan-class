package com.example.gameinfoapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ApiClient.kt
object ApiClient {
    private const val BASE_URL = "https://www.surleweb.xyz/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val gameService: GameService = retrofit.create(GameService::class.java)
}
