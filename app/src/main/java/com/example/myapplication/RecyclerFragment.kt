package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        getMovieData {
            recyclerView.adapter = SuperHeroAdapter(it)
        }

        return view
    }

    private fun getMovieData(callback: (List<Movie>) -> Unit) {
        val apiService = ApiServices.getIntance().create(ApiInterface::class.java)
        apiService.getMovies().enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                response.body()?.results?.let {
                    callback(it)
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {

            }
        })
    }
}
