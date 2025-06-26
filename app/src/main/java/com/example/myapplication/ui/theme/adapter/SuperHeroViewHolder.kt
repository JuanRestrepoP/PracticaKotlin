package com.example.myapplication.ui.theme.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication.R
import com.bumptech.glide.Glide
import com.example.myapplication.Movie
import com.example.myapplication.data.AppDatabase
import com.example.likeutils.data.Like
import com.example.myapplication.data.MovieEntity
import com.example.myapplication.servicies.ApiServices.Companion.IMAGE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuperHeroViewHolder(
    private val view: View,
    private val userEmail: String,
    private val db: AppDatabase
) : RecyclerView.ViewHolder(view) {

    private val superhero = view.findViewById<TextView>(R.id.textView)
    private val marca = view.findViewById<TextView>(R.id.textView2)
    private val realName = view.findViewById<TextView>(R.id.textView3)
    private val foto = view.findViewById<ImageView>(R.id.posterMovie)
    private val likeButton = view.findViewById<LottieAnimationView>(R.id.like_image_view)

    fun render(superHeroModel: Movie) {
        superhero.text = superHeroModel.id.toString()
        marca.text = superHeroModel.overview
        realName.text = superHeroModel.original_title

        val imageUrl = IMAGE_URL + superHeroModel.poster_path
        Glide.with(foto.context).load(imageUrl).into(foto)

        CoroutineScope(Dispatchers.IO).launch {
            val isItemLiked = db.likeDao().isItemLiked(superHeroModel.id, userEmail)
            withContext(Dispatchers.Main) {
                if (isItemLiked) {
                    likeButton.setAnimation(R.raw.bandai_dokkan)
                    likeButton.progress = 1f
                } else {
                    likeButton.setImageResource(R.drawable.like)
                }
            }
        }

        likeButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val liked = db.likeDao().isItemLiked(superHeroModel.id, userEmail)
                if (!liked) {
                    db.movieDao().insert(
                        MovieEntity(
                            id = superHeroModel.id,
                            original_title = superHeroModel.original_title ?: "",
                            overview = superHeroModel.overview ?: "",
                            poster_path = superHeroModel.poster_path ?: ""
                        )
                    )
                    db.likeDao().insert(
                        Like(
                            itemId = superHeroModel.id,
                            userEmail = userEmail
                        )
                    )
                    withContext(Dispatchers.Main) {
                        likeButton.setAnimation(R.raw.bandai_dokkan)
                        likeButton.playAnimation()
                    }
                } else {
                    db.likeDao().deleteLike(superHeroModel.id, userEmail)
                    withContext(Dispatchers.Main) {
                        likeButton.setImageResource(R.drawable.like)
                    }
                }
            }
        }
    }
}