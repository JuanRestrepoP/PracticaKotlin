package com.example.myapplication.ui.theme.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.MovieEntity
import com.example.myapplication.servicies.ApiServices.Companion.IMAGE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewHolder(
    private val view: View,
    private val userEmail: String,
    private val db: AppDatabase,
    private val onLikeChanged: () -> Unit
) : RecyclerView.ViewHolder(view) {

    private val title = view.findViewById<TextView>(R.id.textView)
    private val description = view.findViewById<TextView>(R.id.textView2)
    private val realName = view.findViewById<TextView>(R.id.textView3)
    private val poster = view.findViewById<ImageView>(R.id.posterMovie)
    private val likeButton = view.findViewById<LottieAnimationView>(R.id.like_image_view)

    fun render(movie: MovieEntity) {
        title.text = movie.original_title
        description.text = movie.overview
        realName.text = movie.original_title

        val imageUrl = IMAGE_URL + movie.poster_path
        Glide.with(poster.context).load(imageUrl).into(poster)

        CoroutineScope(Dispatchers.IO).launch {
            val isLiked = db.likeDao().isItemLiked(movie.id, userEmail)
            withContext(Dispatchers.Main) {
                if (isLiked) {
                    likeButton.setAnimation(R.raw.bandai_dokkan)
                    likeButton.progress = 1f
                } else {
                    likeButton.setImageResource(R.drawable.like)
                }
            }
        }

        likeButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val isLiked = db.likeDao().isItemLiked(movie.id, userEmail)
                if (isLiked) {
                    db.likeDao().deleteLike(movie.id, userEmail)
                    withContext(Dispatchers.Main) { onLikeChanged() }
                }
            }
        }
    }
}
