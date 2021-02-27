package com.mycapstoneprojectsubmissiontwo.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreData(
    val genreId: Int,
    val movieId: Int,
    val genreName: String
): Parcelable