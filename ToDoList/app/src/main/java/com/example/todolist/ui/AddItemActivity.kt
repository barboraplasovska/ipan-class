package com.example.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.models.Todo

class AddItemActivity : AppCompatActivity() {

    private lateinit var todoInput: EditText
    private lateinit var deleteButton: Button
    private lateinit var validateButton: Button

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        // Retrieve user ID from intent
        userId = intent.getIntExtra("userId", -1)

        // Initialize views
        todoInput = findViewById(R.id.todoInput)
        deleteButton = findViewById(R.id.deleteButton)
        validateButton = findViewById(R.id.validateButton)

        // Set onClickListener for delete button
        deleteButton.setOnClickListener {
            todoInput.text.clear()
        }

        // Set onClickListener for validate button
        validateButton.setOnClickListener {
            val todoText = todoInput.text.toString().trim()
            if (todoText.isNotEmpty()) {

                // After saving, navigate back to ListActivity with updated data
                val intent = Intent(this@AddItemActivity, ListActivity::class.java)
                intent.putExtra("userId", userId)
                intent.putExtra("newTodoTitle", todoText)
                startActivity(intent)
                finish()
            }
        }
    }
}
