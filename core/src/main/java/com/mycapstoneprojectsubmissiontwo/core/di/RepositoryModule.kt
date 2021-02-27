package com.mycapstoneprojectsubmissiontwo.core.di

import com.mycapstoneprojectsubmissiontwo.core.data.MovieRepository
import com.mycapstoneprojectsubmissiontwo.core.domain.repository.IMovieRepository
import com.mycapstoneprojectsubmissiontwo.core.domain.usecase.MovieInteractor
import com.mycapstoneprojectsubmissiontwo.core.domain.usecase.MoviesUseCase
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(movieRepository: MovieRepository): IMovieRepository

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MoviesUseCase
}