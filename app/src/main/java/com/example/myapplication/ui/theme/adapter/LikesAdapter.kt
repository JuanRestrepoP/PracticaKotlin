package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.MovieEntity
import com.example.myapplication.ui.theme.adapter.MovieViewHolder

class LikesAdapter(
    private val email: String,
    private val db: AppDatabase,
    private val onLikeChanged: () -> Unit
) : ListAdapter<MovieEntity, MovieViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_like, parent, false)
        return MovieViewHolder(view, email, db, onLikeChanged)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.render(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem == newItem
    }
}
