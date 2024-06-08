package com.example.liststp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyListTextView: TextView
    private lateinit var adapter: MovieCharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        emptyListTextView = findViewById(R.id.activity_main_txt_emptylist)

        val data: MutableList<MovieCharacter> = arrayListOf(
            MovieCharacter("Han", "Solo", "Star Wars", true),
            MovieCharacter("Darth", "Vader", "Star Wars", false)
        )

        adapter = MovieCharacterAdapter(this, data, View.OnClickListener { view ->
            val position = view.tag as Int
            val clickedItem = data[position]
            Toast.makeText(this, "Clicked: ${clickedItem.firstName} ${clickedItem.lastName}", Toast.LENGTH_SHORT).show()
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter

        toggleEmptyView(data.isEmpty())
    }

    private fun toggleEmptyView(isEmpty: Boolean) {
        if (isEmpty) {
            emptyListTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyListTextView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}