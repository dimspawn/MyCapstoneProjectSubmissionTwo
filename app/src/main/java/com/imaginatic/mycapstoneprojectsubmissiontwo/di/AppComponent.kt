package com.imaginatic.mycapstoneprojectsubmissiontwo.di

import com.imaginatic.mycapstoneprojectsubmissiontwo.detail.DetailMovieActivity
import com.imaginatic.mycapstoneprojectsubmissiontwo.home.HomeFragment
import com.mycapstoneprojectsubmissiontwo.core.di.CoreComponent
import com.mycapstoneprojectsubmissiontwo.core.domain.usecase.MoviesUseCase
import dagger.Component

@AppScope
@Component(
    modules = [ViewModelModule::class], dependencies = [CoreComponent::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): AppComponent
    }

    fun provideMoviesUseCase(): MoviesUseCase

    fun inject(fragment: HomeFragment)
    fun inject(activity: DetailMovieActivity)
}