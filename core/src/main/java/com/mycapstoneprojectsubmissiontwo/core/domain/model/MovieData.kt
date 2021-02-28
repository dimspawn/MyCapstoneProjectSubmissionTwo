package com.mycapstoneprojectsubmissiontwo.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieData(
        val movieId: Int,
        val movieDescription: String,
        val movieReleaseDate: String,
        val movieTitle: String,
        val movieIsAdult: Boolean,
        val movieRating: Double,
        val movieBackgroundImage: String,
        val movieLandscapeImage: String,
        var movieIsVisited: Boolean,
        val movieIsFavorite: Boolean,
        val movieDate: String,
        var movieIsSearchResult: Boolean,
        val movieIsHomeResult: Boolean
): Parcelable