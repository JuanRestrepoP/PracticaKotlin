package com.example.myapplication.ui.theme.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.MovieWithLikes

class TopMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.textView)
    private val overview = view.findViewById<TextView>(R.id.textView2)
    private val description = view.findViewById<TextView>(R.id.textView3)
    private val poster = view.findViewById<ImageView>(R.id.posterMovie)
    private val likeCount = view.findViewById<TextView>(R.id.textViewLikes)

    fun bind(movieWithLikes: MovieWithLikes) {
        val movie = movieWithLikes.movie
        title.text = movie.original_title
        overview.text = movie.overview
        description.text = "ID: ${movie.id}"
        likeCount.text = "${movieWithLikes.likeCount} likes"

        Glide.with(poster.context)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .into(poster)
    }
}
