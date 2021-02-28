package com.mycapstoneprojectsubmissiontwo.core.di

import android.content.Context
import androidx.room.Room
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.room.MovieDao
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Module
class DatabaseModule {
    @CoreScope
    @Provides
    fun provideDatabase(context: Context): MovieDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("imaginatic".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "Movies.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun providesGameDao(database: MovieDatabase): MovieDao = database.movieDao()
}