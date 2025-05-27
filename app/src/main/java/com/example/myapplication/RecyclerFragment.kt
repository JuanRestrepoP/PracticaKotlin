package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Like
import com.example.myapplication.servicies.ApiInterface
import com.example.myapplication.servicies.ApiServices
import com.example.myapplication.ui.theme.adapter.SuperHeroAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recycler, container, false)

        recyclerView = view.findViewById(R.id.recyclerSuperHero)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val prefs = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)

        if (email != null) {
            val db = AppDatabase.getDatabase(requireContext())

            getMovieData { movies ->
                recyclerView.adapter = SuperHeroAdapter(
                    superheroList = movies,
                    userEmail = email,
                    db = db
                )
            }
        }

        return view
    }

    private fun getMovieData(callback: (List<Movie>) -> Unit) {
        val apiService = ApiServices.getIntance().create(ApiInterface::class.java)
        apiService.getMovies().enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                response.body()?.results?.let { callback(it) }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(requireContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

