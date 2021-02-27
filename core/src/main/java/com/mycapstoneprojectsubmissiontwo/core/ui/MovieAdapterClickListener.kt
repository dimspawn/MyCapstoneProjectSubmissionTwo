package com.mycapstoneprojectsubmissiontwo.core.ui

import com.mycapstoneprojectsubmissiontwo.core.domain.model.MovieData

interface MovieAdapterClickListener {
    fun onMovieClickListener(movieData: MovieData)
}