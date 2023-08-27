package com.mycapstoneprojectsubmissiontwo.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycapstoneprojectsubmissiontwo.core.di.ViewModelKey
import com.mycapstoneprojectsubmissiontwo.core.ui.ViewModelFactory
import com.mycapstoneprojectsubmissiontwo.favorite.favorite.FavoriteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewFavoriteModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}