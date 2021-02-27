package com.imaginatic.mycapstoneprojectsubmissiontwo.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mycapstoneprojectsubmissiontwo.core.domain.model.MovieData
import com.mycapstoneprojectsubmissiontwo.core.domain.usecase.MoviesUseCase
import javax.inject.Inject

class DetailMoviesViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) : ViewModel() {
    private val movieId = MutableLiveData<Int>()

    val detailMovie = Transformations.switchMap(movieId) { movieIds ->
        moviesUseCase.getMovieDetailByMovieId(movieIds).asLiveData()
    }

    val genreMovie = Transformations.switchMap(movieId) { movieIds ->
        moviesUseCase.getGenreByMovieId(movieIds).asLiveData()
    }

    fun setMovieId(movieId: Int) {
        this.movieId.value = movieId
    }

    fun setFavoriteMovie(movie: MovieData, newStatus: Boolean) = moviesUseCase.setFavoriteMovie(movie, newStatus)
}