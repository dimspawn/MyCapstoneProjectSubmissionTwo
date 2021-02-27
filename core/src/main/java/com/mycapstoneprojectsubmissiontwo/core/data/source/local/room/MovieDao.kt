package com.mycapstoneprojectsubmissiontwo.core.data.source.local.room

import androidx.room.*
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.entity.MovieEntity
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.entity.MovieGenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMoviesLiterally(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE isHomeResult = 1 AND isSearchResult = 0 AND isFavorite = 0")
    fun getTodayMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE isHomeResult = 1")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE isSearchResult = 1")
    fun getSearchAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE movieId = :movieId")
    fun getMovieDetailByMovieId(movieId: Int): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE isFavorite = 1 AND movieTitle LIKE '%' || :movieName || '%'")
    fun getSearchFavorite(movieName: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM genre WHERE movieId = :movieId")
    fun getMovieGenresByMovieId(movieId: Int): Flow<List<MovieGenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(genres: List<MovieGenreEntity>)

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)

    @Update
    fun updateMovies(movies: List<MovieEntity>)

    @Query("UPDATE movie SET isVisited = :isUpdated, todayDate = :todayDate WHERE movieId = :movieId")
    fun updateDetailMovie(movieId: Int, isUpdated: Boolean, todayDate: String)

    @Query("DELETE FROM movie WHERE todayDate = :date")
    fun deleteAllTodayGames(date: String)
}