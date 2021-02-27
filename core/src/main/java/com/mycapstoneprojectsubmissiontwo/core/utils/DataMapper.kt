package com.mycapstoneprojectsubmissiontwo.core.utils

import android.annotation.SuppressLint
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.entity.MovieEntity
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.entity.MovieGenreEntity
import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.response.MovieGenreResponse
import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.response.MovieResponse
import com.mycapstoneprojectsubmissiontwo.core.domain.model.GenreData
import com.mycapstoneprojectsubmissiontwo.core.domain.model.MovieData
import java.text.SimpleDateFormat
import java.util.*

object DataMapper {
    fun mapMovieEntitiesToDomains(movieEntities: List<MovieEntity>): List<MovieData> = movieEntities.map {
        MovieData(
            movieId = it.movieId,
            movieDescription = it.movieDescription,
            movieReleaseDate = it.movieReleaseDate,
            movieTitle = it.movieTitle,
            movieIsAdult = it.movieIsAdult,
            movieRating = it.movieRating,
            movieBackgroundImage = it.movieBackgroundImage,
            movieLandscapeImage = it.movieLandscapeImage,
            movieIsVisited = it.movieIsVisited,
            movieIsFavorite = it.movieIsFavorite,
            movieDate = it.movieTodayDate,
            movieIsSearchResult = it.movieIsSearchResult,
            movieIsHomeResult = it.movieIsHomeResult
        )
    }

    fun mapSearchInsertMovieResponseToEntity(input: MovieResponse): MovieEntity = MovieEntity(
        movieId = input.id ?: 0,
        movieDescription = input.description ?: "No Description",
        movieReleaseDate = input.releaseDate ?: "TBA",
        movieTitle = input.title ?: "No Title",
        movieIsAdult = input.isAdult ?: false,
        movieRating = input.rating ?: 0.0,
        movieBackgroundImage = input.backgroundImage ?: "",
        movieLandscapeImage = input.landscapeImage ?: "",
        movieTodayDate = getTodayDate(),
        movieIsSearchResult = true,
        movieIsHomeResult = false
    )

    fun mapSearchUpdateMovieResponseToEntity(movieResponse: MovieResponse, movieData: MovieData): MovieEntity = MovieEntity(
        movieId = movieResponse.id ?: movieData.movieId,
        movieDescription = movieResponse.description ?: movieData.movieDescription,
        movieReleaseDate = movieResponse.releaseDate ?: movieData.movieReleaseDate,
        movieTitle = movieResponse.title ?: movieData.movieTitle,
        movieIsAdult = movieResponse.isAdult ?: movieData.movieIsAdult,
        movieRating = movieResponse.rating ?: movieData.movieRating,
        movieBackgroundImage = movieResponse.backgroundImage ?: movieData.movieBackgroundImage,
        movieLandscapeImage = movieResponse.landscapeImage ?: movieData.movieLandscapeImage,
        movieIsVisited = movieData.movieIsVisited,
        movieIsFavorite = movieData.movieIsFavorite,
        movieTodayDate = getTodayDate(),
        movieIsSearchResult = true,
        movieIsHomeResult = movieData.movieIsHomeResult
    )

    fun mapMovieDomainsToEntities(input: List<MovieData>): List<MovieEntity> = input.map {
        MovieEntity(
            movieId = it.movieId,
            movieDescription = it.movieDescription,
            movieReleaseDate = it.movieReleaseDate,
            movieTitle = it.movieTitle,
            movieIsAdult = it.movieIsAdult,
            movieRating = it.movieRating,
            movieBackgroundImage = it.movieBackgroundImage,
            movieLandscapeImage = it.movieLandscapeImage,
            movieIsVisited = it.movieIsVisited,
            movieIsFavorite = it.movieIsFavorite,
            movieTodayDate = it.movieDate,
            movieIsSearchResult = it.movieIsSearchResult,
            movieIsHomeResult = it.movieIsHomeResult
        )
    }

    fun mapMovieDomainToEntity(input: MovieData) = MovieEntity(
        movieId = input.movieId,
        movieDescription = input.movieDescription,
        movieReleaseDate = input.movieReleaseDate,
        movieTitle = input.movieTitle,
        movieIsAdult = input.movieIsAdult,
        movieRating = input.movieRating,
        movieBackgroundImage = input.movieBackgroundImage,
        movieLandscapeImage = input.movieLandscapeImage,
        movieIsVisited = input.movieIsVisited,
        movieIsFavorite = input.movieIsFavorite,
        movieTodayDate = input.movieDate,
        movieIsSearchResult = input.movieIsSearchResult,
        movieIsHomeResult = input.movieIsHomeResult
    )

    fun mapMovieResponsesToEntities(movieResponses: List<MovieResponse>): List<MovieEntity> = movieResponses.map {
        MovieEntity(
            movieId = it.id ?: 0,
            movieDescription = it.description ?: "No Description",
            movieReleaseDate = it.releaseDate ?: "TBA",
            movieTitle = it.title ?: "No Title",
            movieIsAdult = it.isAdult ?: false,
            movieRating = it.rating ?: 0.0,
            movieBackgroundImage = it.backgroundImage ?: "",
            movieLandscapeImage = it.landscapeImage ?: "",
            movieTodayDate = getTodayDate()
        )
    }

    fun mapGenreResponsesToEntities(input: List<MovieGenreResponse>, movieId: Int): List<MovieGenreEntity> =
        input.map {
            MovieGenreEntity(
                movieId = movieId,
                genreName = it.genreName ?: "[Unknown]"
            )
        }

    fun mapGenreEntitiesToDomains(input: List<MovieGenreEntity>): List<GenreData> =
       input.map {
           GenreData(
               genreId = it.genreId,
               movieId = it.movieId,
               genreName = it.genreName
           )
       }

    @SuppressLint("SimpleDateFormat")
    fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(date: String): String {
        val dateRes = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val sdf = SimpleDateFormat("dd MMM yyyy")
        return if (dateRes != null) { sdf.format(dateRes)} else { date }
    }
}