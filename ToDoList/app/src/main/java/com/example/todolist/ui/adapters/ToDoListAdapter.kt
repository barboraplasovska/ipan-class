package com.example.todolist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.models.Todo

class ToDoListAdapter(private val itemList: ArrayList<Todo>, private val onItemCheckedChanged: (Todo) -> Unit) :
    RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = itemList[position]
        holder.itemTextView.text = todo.title
        holder.checkBox.isChecked = todo.completed

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            todo.completed = isChecked
            onItemCheckedChanged(todo)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.itemTextView)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }
}
