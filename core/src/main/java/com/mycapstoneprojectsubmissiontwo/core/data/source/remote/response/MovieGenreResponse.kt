package com.mycapstoneprojectsubmissiontwo.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieGenreResponse(
    @field:SerializedName("name")
    val genreName: String? = null
)