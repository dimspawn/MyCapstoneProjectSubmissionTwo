package com.mycapstoneprojectsubmissiontwo.core.di

import android.content.Context
import com.mycapstoneprojectsubmissiontwo.core.domain.repository.IMovieRepository
import com.mycapstoneprojectsubmissiontwo.core.domain.usecase.MoviesUseCase
import dagger.BindsInstance
import dagger.Component

@CoreScope
@Component(modules = [RepositoryModule::class])
interface CoreComponent {
    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun build(): CoreComponent
    }

    fun provideMoviesUseCase(): MoviesUseCase
    fun provideRepository(): IMovieRepository
}