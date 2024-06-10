package com.example.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
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
    private lateinit var progressBar: ProgressBar
    private lateinit var greetingsTextView: TextView
    private lateinit var helloAgainTextView: TextView
    private lateinit var totalItemsText: TextView
    private lateinit var doneItemsText: TextView
    private lateinit var notDoneItemsText: TextView

    private lateinit var todos: ArrayList<Todo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // Retrieve user ID from intent
        val userId = intent.getIntExtra("userId", -1)
        val newTodo = intent.getStringExtra("newTodoTitle")

        progressBar = findViewById(R.id.progressBar)
        greetingsTextView = findViewById<TextView>(R.id.greetingsTextview)
       helloAgainTextView = findViewById<TextView>(R.id.helloAgainTextView)
        totalItemsText = findViewById<TextView>(R.id.totalItemsText)
        doneItemsText = findViewById<TextView>(R.id.doneItemsText)
        notDoneItemsText = findViewById<TextView>(R.id.todoItemsText)

        // Show the ProgressBar
        toggleProgressBarVisibility(true);

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
                    adapter = ToDoListAdapter(todos) { updatedTodo ->
                        // Update the counts and UI here
                        val totalItems = todos.size
                        val completed = todos.count { it.completed }
                        totalItemsText.text = "$totalItems items total"
                        doneItemsText.text = "$completed Done"
                        notDoneItemsText.text = "${totalItems - completed} ToDo"
                    }
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@ListActivity)

                    // Set greetings text
                    val userName = getUserDisplayName(userId)
                    val greetingsText = "Hello <b>$userName</b>, here is what lies in your todo list for today:"
                    greetingsTextView.text = HtmlCompat.fromHtml(greetingsText, HtmlCompat.FROM_HTML_MODE_LEGACY)

                    // Set hello again text
                    helloAgainTextView.text = getString(R.string.greetings2)
                    // Set total items text

                    val totalItems = todos.size
                    val completed = todos.filter { todo -> todo.completed }.count()
                    totalItemsText.text = "$totalItems items total"


                    doneItemsText.text = "$completed Done"

                    notDoneItemsText.text = "${totalItems - completed} ToDo"

                    withContext(Dispatchers.Main) {
                        // Hide the ProgressBar in case of error
                        toggleProgressBarVisibility(false);
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Hide the ProgressBar in case of error
                   toggleProgressBarVisibility(false);
                }
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

    private fun toggleProgressBarVisibility(showProgressBar: Boolean) {
        if (showProgressBar) {
            progressBar.visibility = View.VISIBLE
            greetingsTextView.visibility = View.GONE
            helloAgainTextView.visibility = View.GONE
            doneItemsText.visibility = View.GONE
            totalItemsText.visibility = View.GONE
            notDoneItemsText.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            greetingsTextView.visibility = View.VISIBLE
            helloAgainTextView.visibility = View.VISIBLE
            doneItemsText.visibility = View.VISIBLE
            totalItemsText.visibility = View.VISIBLE
            notDoneItemsText.visibility = View.VISIBLE
        }
    }

}
