package com.example.myapplication.ui.theme.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.bumptech.glide.Glide
import com.example.myapplication.Movie
import com.example.myapplication.servicies.ApiServices.Companion.IMAGE_URL

class SuperHeroViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val superhero = view.findViewById<TextView>(R.id.textView)
    val marca = view.findViewById<TextView>(R.id.textView2)
    val realName = view.findViewById<TextView>(R.id.textView3)
    val foto =view.findViewById<ImageView>(R.id.imageView)

    fun render(superHeroModel: Movie){
        superhero.text=superHeroModel.id.toString()
        marca.text=superHeroModel.overview
        realName.text=superHeroModel.original_title
        val imageUrl= IMAGE_URL + superHeroModel.poster_path
        Glide.with(foto.context).load(imageUrl).into(foto)
    }
}