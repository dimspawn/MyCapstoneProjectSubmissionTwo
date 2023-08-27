package com.mycapstoneprojectsubmissiontwo.favorite.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mycapstoneprojectsubmissiontwo.core.domain.usecase.MoviesUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase): ViewModel(){
    private val movieName = MutableLiveData<String>()
    val favoriteMovies = moviesUseCase.getFavoriteMovie().asLiveData()

    val searchFavoriteMovies = Transformations.switchMap(movieName) { movieNameFavorite ->
        moviesUseCase.getSearchFavorite(movieNameFavorite).asLiveData()
    }

    fun setMovieName(movieName: String) {
        this.movieName.value = movieName
    }
}