package com.mycapstoneprojectsubmissiontwo.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: Int,
    @ColumnInfo(name = "movieDescription")
    var movieDescription: String,
    @ColumnInfo(name = "movieReleaseDate")
    var movieReleaseDate: String,
    @ColumnInfo(name = "movieTitle")
    var movieTitle: String,
    @ColumnInfo(name = "movieIsAdult")
    var movieIsAdult: Boolean,
    @ColumnInfo(name = "movieRating")
    var movieRating: Double,
    @ColumnInfo(name = "movieBackgroundImage")
    var movieBackgroundImage: String,
    @ColumnInfo(name = "movieLandscapeImage")
    var movieLandscapeImage: String,
    @ColumnInfo(name = "isVisited")
    var movieIsVisited: Boolean = false,
    @ColumnInfo(name = "isFavorite")
    var movieIsFavorite: Boolean = false,
    @ColumnInfo(name = "todayDate")
    var movieTodayDate: String,
    @ColumnInfo(name = "isSearchResult")
    var movieIsSearchResult: Boolean = false,
    @ColumnInfo(name = "isHomeResult")
    var movieIsHomeResult: Boolean = true
): Parcelable