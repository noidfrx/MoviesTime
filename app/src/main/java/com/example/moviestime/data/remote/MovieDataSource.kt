package com.example.moviestime.data.remote

import com.example.moviestime.application.AppConstants.API_KEY
import com.example.moviestime.application.AppConstants.DEFAULT_LAN
import com.example.moviestime.data.model.MovieList
import com.example.moviestime.repository.WebService

class MovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies() : MovieList = webService.getUpcomingMovies(API_KEY, DEFAULT_LAN)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(API_KEY, DEFAULT_LAN)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(API_KEY, DEFAULT_LAN)
}