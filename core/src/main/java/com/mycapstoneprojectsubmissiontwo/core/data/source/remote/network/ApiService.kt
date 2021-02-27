package com.mycapstoneprojectsubmissiontwo.core.data.source.remote.network

import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.response.ListMovieResponse
import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/trending/movie/day")
    suspend fun getTrendingMovie(@Query("api_key") apiKey: String): ListMovieResponse

    @GET("3/movie/{movieId}")
    suspend fun getDetailMovie(@Path("movieId") movieId: Int, @Query("api_key") apiKey: String): MovieResponse

    @GET("3/search/movie")
    suspend fun getSearchMovie(@Query("api_key") apiKey: String, @Query("query") query: String): ListMovieResponse
}