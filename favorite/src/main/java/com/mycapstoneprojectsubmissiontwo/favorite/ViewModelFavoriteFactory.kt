package com.mycapstoneprojectsubmissiontwo.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycapstoneprojectsubmissiontwo.core.domain.usecase.MoviesUseCase
import javax.inject.Inject

class ViewModelFavoriteFactory @Inject constructor(private val moviesUseCase: MoviesUseCase): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(moviesUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}