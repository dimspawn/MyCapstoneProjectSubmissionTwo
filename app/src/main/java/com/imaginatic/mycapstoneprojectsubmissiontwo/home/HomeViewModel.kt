package com.imaginatic.mycapstoneprojectsubmissiontwo.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mycapstoneprojectsubmissiontwo.core.domain.usecase.MoviesUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase): ViewModel() {
    private val movieName = MutableLiveData<String>()
    val movies = moviesUseCase.getAllMovies().asLiveData()

    val searchMovies = Transformations.switchMap(movieName) { movieSearchName ->
        moviesUseCase.getSearchMovies(movieSearchName).asLiveData()
    }

    fun setMovieName(movieName: String) {
        this.movieName.value = movieName
    }
}