package com.imaginatic.mycapstoneprojectsubmissiontwo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imaginatic.mycapstoneprojectsubmissiontwo.detail.DetailMoviesViewModel
import com.imaginatic.mycapstoneprojectsubmissiontwo.home.HomeViewModel
import com.mycapstoneprojectsubmissiontwo.core.ui.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailMoviesViewModel::class)
    abstract fun bindDetailMoviesViewModel(viewModel: DetailMoviesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}