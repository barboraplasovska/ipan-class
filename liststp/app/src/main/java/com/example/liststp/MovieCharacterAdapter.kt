package com.example.liststp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieCharacterAdapter(
    private val context: Context,
    private val data: List<MovieCharacter>,
    private val onItemClickListener: View.OnClickListener
) : RecyclerView.Adapter<MovieCharacterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val movieTextView: TextView = itemView.findViewById(R.id.movie)
        val roleImageView: ImageView = itemView.findViewById(R.id.role)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        view.setOnClickListener(onItemClickListener)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.nameTextView.text = "${currentItem.firstName} ${currentItem.lastName}"
        holder.movieTextView.text = currentItem.movie
        holder.roleImageView.setImageResource(if (currentItem.goodGuy) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off)
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
