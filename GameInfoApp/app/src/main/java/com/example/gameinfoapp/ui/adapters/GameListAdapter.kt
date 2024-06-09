package com.example.gameinfoapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gameinfoapp.R
import com.example.gameinfoapp.models.Game

class GameListAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    private val games = mutableListOf<Game>()

    fun updateList(newGames: List<Game>) {
        games.clear()
        games.addAll(newGames)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = games[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.gameImage)
        private val textView: TextView = itemView.findViewById(R.id.gameTitle)

        fun bind(game: Game) {
            textView.text = game.name
            Glide.with(itemView.context)
                .load(game.picture)
                .into(imageView)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
