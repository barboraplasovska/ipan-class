package com.example.todolist.models

data class Todo(
    val id: Int,
    val userId: Int,
    val title: String,
    var completed: Boolean,
)
