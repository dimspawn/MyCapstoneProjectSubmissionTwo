package com.mycapstoneprojectsubmissiontwo.core.di

import android.content.Context
import androidx.room.Room
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.room.MovieDao
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.room.MovieDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @CoreScope
    @Provides
    fun provideDatabase(context: Context): MovieDatabase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java, "Movies.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun providesGameDao(database: MovieDatabase): MovieDao = database.movieDao()
}