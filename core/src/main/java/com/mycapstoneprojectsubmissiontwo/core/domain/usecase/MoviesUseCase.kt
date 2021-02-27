package com.mycapstoneprojectsubmissiontwo.core.domain.usecase

import com.mycapstoneprojectsubmissiontwo.core.data.Resource
import com.mycapstoneprojectsubmissiontwo.core.domain.model.GenreData
import com.mycapstoneprojectsubmissiontwo.core.domain.model.MovieData
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    fun getAllMovies(): Flow<Resource<List<MovieData>>>
    fun getSearchMovies(movieName: String): Flow<Resource<List<MovieData>>>
    fun getMovieDetailByMovieId(movieId: Int): Flow<Resource<List<MovieData>>>
    fun getGenreByMovieId(movieId: Int): Flow<List<GenreData>>
    fun getFavoriteMovie(): Flow<List<MovieData>>
    fun getSearchFavorite(movieName: String): Flow<List<MovieData>>
    fun setFavoriteMovie(movieData: MovieData, state: Boolean)
}