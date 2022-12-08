package com.example.moviestime.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import com.example.moviestime.R
import com.example.moviestime.core.Resource
import com.example.moviestime.data.model.Movie
import com.example.moviestime.data.remote.MovieDataSource
import com.example.moviestime.databinding.FragmentMovieBinding
import com.example.moviestime.presentation.MovieViewModel
import com.example.moviestime.presentation.MovieViewModelFactory
import com.example.moviestime.repository.MovieRepositoryImpl
import com.example.moviestime.repository.RetrofitClient
import com.example.moviestime.ui.movie.adapters.MovieAdapter
import com.example.moviestime.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.moviestime.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.moviestime.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var concatAdapter: ConcatAdapter

    //INYECCIÒN DE DEPENDENCIAS MANUAL (No tener logica de implementacion en cada modulo)
    //By delegador, le doy un trabajo para que lo haga, crear instancia de VM
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(
                    RetrofitClient.webService
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        //Cada vez que haga emit() nosotros vamos a escuchar lo que nos dice
        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner){
            when (it){
                is Resource.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success ->{
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0, UpcomingConcatAdapter(MovieAdapter(it.data.first.results, this@MovieFragment)))
                        addAdapter(1, TopRatedConcatAdapter(MovieAdapter(it.data.second.results, this@MovieFragment)))
                        addAdapter(2, PopularConcatAdapter(MovieAdapter(it.data.third.results, this@MovieFragment)))
                    }
                    binding.rvMovies.adapter = concatAdapter

                }
                is Resource.Failure ->{
                    binding.progressBar.visibility = View.GONE
                    Log.d("TesteandoAndo", "ERROR ${it.exception}")
                }

            }
        }
    }

    override fun onMovieClicked(movie: Movie) {
        Log.d("TesteandoAndo", "Click en: $movie")
    }
}