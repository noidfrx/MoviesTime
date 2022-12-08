package com.example.moviestime.repository

import com.example.moviestime.data.model.MovieList

interface MovieRepository {

    suspend fun getUpcomingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList
}