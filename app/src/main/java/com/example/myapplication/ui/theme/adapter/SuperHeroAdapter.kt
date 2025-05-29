package com.example.myapplication.ui.theme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Movie
import com.example.myapplication.R
import com.example.myapplication.data.AppDatabase

class SuperHeroAdapter(
    private val superheroList: List<Movie>,
    private val userEmail: String,
    private val db: AppDatabase
) : RecyclerView.Adapter<SuperHeroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_superhero, parent, false)
        return SuperHeroViewHolder(view, userEmail, db)
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        val item = superheroList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = superheroList.size
}
