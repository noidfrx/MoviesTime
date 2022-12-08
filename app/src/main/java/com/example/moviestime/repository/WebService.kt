package com.example.moviestime.repository

import com.example.moviestime.application.AppConstants.BASE_URL
import com.example.moviestime.data.model.MovieList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    //Mismos metodos que en repo. Usa retrofit para traer info

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey : String,
                                  @Query("language") language : String): MovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey : String,
                                  @Query("language") language : String): MovieList

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey : String,
                                 @Query("language") language : String): MovieList
}

object RetrofitClient{

    //By lazy inicializa la variable solamente en el momento de llamar a webService
    val webService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().create()
                ))
            .build().create(WebService::class.java)
    }
}
