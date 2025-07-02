package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.MovieWithLikes
import com.example.myapplication.ui.theme.adapter.TopMoviesAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var adapter: TopMoviesAdapter
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getDatabase(requireContext())

        adapter = TopMoviesAdapter()
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerTopLiked)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        parentFragmentManager.setFragmentResultListener("likeChanged", viewLifecycleOwner) { _, _ ->
            reloadTopLikes()
        }
    }

    override fun onResume() {
        super.onResume()
        reloadTopLikes()
    }

            private fun reloadTopLikes() {
                viewLifecycleOwner.lifecycleScope.launch {
                    val topLikes = db.likeDao().getTopLikedItems()
                    val ids = topLikes.map { it.itemId }

                    val movies = db.movieDao().getLikedMovies(ids)
                    val movieMap = movies.associateBy { it.id }

                    val movieWithLikesList = topLikes.mapNotNull { topLike ->
                        movieMap[topLike.itemId]?.let { movie ->
                            MovieWithLikes(movie, topLike.likeCount)
                        }
                    }.sortedByDescending { it.likeCount }
                    adapter.submitList(movieWithLikesList)
                }
            }
        }





