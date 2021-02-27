package com.mycapstoneprojectsubmissiontwo.core.data.source.local

import com.mycapstoneprojectsubmissiontwo.core.data.source.local.entity.MovieEntity
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.entity.MovieGenreEntity
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.room.MovieDao
import com.mycapstoneprojectsubmissiontwo.core.di.CoreScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@CoreScope
class LocalDataSource @Inject constructor(private val movieDao: MovieDao){
    fun getAllMoviesLiterally(): Flow<List<MovieEntity>> = movieDao.getAllMoviesLiterally()
    fun getTodayMovies(): Flow<List<MovieEntity>> = movieDao.getTodayMovies()
    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()
    fun getSearchAllMovies(): Flow<List<MovieEntity>> = movieDao.getSearchAllMovies()
    fun getMovieDetailByMovieId(movieId: Int): Flow<List<MovieEntity>> = movieDao.getMovieDetailByMovieId(movieId)
    fun getMovieGenresByMovieId(movieId: Int): Flow<List<MovieGenreEntity>> = movieDao.getMovieGenresByMovieId(movieId)
    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()
    fun getSearchFavorite(movieName: String): Flow<List<MovieEntity>> = movieDao.getSearchFavorite(movieName)
    suspend fun insertMovie(movieList: List<MovieEntity>) { movieDao.insertMovies(movieList)}
    suspend fun insertGenres(genreList: List<MovieGenreEntity>) { movieDao.insertGenres(genreList)}
    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.movieIsFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
    fun setUpdateMovies(movies: List<MovieEntity>) {
        movieDao.updateMovies(movies)
    }
    fun setUpdateDetailMovie(movie: MovieEntity) {
        movieDao.updateDetailMovie(movie.movieId, true, movie.movieTodayDate)
    }
    fun deleteAllTodayGames(date: String) { movieDao.deleteAllTodayGames(date) }
}