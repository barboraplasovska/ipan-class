package com.example.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.models.Todo
import com.example.todolist.network.ApiClient
import com.example.todolist.ui.adapters.ToDoListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ToDoListAdapter

    private lateinit var todos: ArrayList<Todo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // Retrieve user ID from intent
        val userId = intent.getIntExtra("userId", -1)
        val newTodo = intent.getStringExtra("newTodoTitle")

        // Fetch user's todo list using Retrofit
        CoroutineScope(Dispatchers.IO).launch {
            try {
                todos = ApiClient.todoService.getUsersTodosList(userId) as ArrayList<Todo>
                if (newTodo != null) {
                    todos.add(Todo(
                        todos.size + 1,
                        userId,
                        newTodo,
                        false
                    ))
                }
                withContext(Dispatchers.Main) {
                    // Initialize RecyclerView
                    recyclerView = findViewById(R.id.recyclerView)
                    adapter = ToDoListAdapter(todos)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@ListActivity)

                    // Set greetings text
                    val greetingsTextView = findViewById<TextView>(R.id.greetingsTextview)
                    val userName = getUserDisplayName(userId)
                    val greetingsText = "Hello <b>$userName</b>, here is what lies in your todo list for today:"
                    greetingsTextView.text = HtmlCompat.fromHtml(greetingsText, HtmlCompat.FROM_HTML_MODE_LEGACY)

                    // Set hello again text
                    val helloAgainTextView = findViewById<TextView>(R.id.helloAgainTextView)
                    helloAgainTextView.text = getString(R.string.greetings2)

                    // Set total items text
                    val totalItemsText = findViewById<TextView>(R.id.totalItemsText)
                    val totalItems = todos.size
                    val completed = todos.filter { todo -> todo.completed }.count()
                    totalItemsText.text = "$totalItems items total"

                    val doneItemsText = findViewById<TextView>(R.id.doneItemsText)
                    doneItemsText.text = "$completed Done"

                    val notDoneItemsText = findViewById<TextView>(R.id.todoItemsText)
                    notDoneItemsText.text = "${totalItems - completed} ToDo"
                }
            } catch (e: Exception) {
                Log.e("ListActivity", "Error fetching todo list: ${e.message}")
            }
        }

        // Add click listener to the add button
        val addTodoButton = findViewById<Button>(R.id.addTodoButton)
        addTodoButton.setOnClickListener {
            val intent = Intent(this@ListActivity, AddItemActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }

    // Function to get the display name of the user
    private fun getUserDisplayName(userId: Int): String {
        return when (userId) {
            1 -> getString(R.string.mom)
            2 -> getString(R.string.dad)
            3 -> getString(R.string.me)
            4 -> getString(R.string.sister)
            9 -> getString(R.string.guest)
            else -> "User"
        }
    }
}
