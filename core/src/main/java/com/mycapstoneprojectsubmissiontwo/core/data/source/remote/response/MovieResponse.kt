package com.mycapstoneprojectsubmissiontwo.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("overview")
    val description: String? = null,
    @field:SerializedName("release_date")
    val releaseDate: String? = null,
    @field:SerializedName("title")
    val title: String? = null,
    @field:SerializedName("adult")
    val isAdult: Boolean? = null,
    @field:SerializedName("vote_average")
    val rating: Double? = null,
    @field:SerializedName("poster_path")
    val backgroundImage: String? = null,
    @field:SerializedName("backdrop_path")
    val landscapeImage: String? = null,
    @field:SerializedName("genres")
    val genres: List<MovieGenreResponse>
)