package com.example.todolist.network

import com.example.todolist.models.Todo
import retrofit2.http.GET
import retrofit2.http.Query

interface TodoService {
    @GET("todos")
    suspend fun getTodosList(): List<Todo>

    @GET("todos")
    suspend fun getUsersTodosList(@Query("userId") userId: Int): List<Todo>
}