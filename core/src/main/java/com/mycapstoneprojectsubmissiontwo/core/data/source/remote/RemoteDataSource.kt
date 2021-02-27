package com.mycapstoneprojectsubmissiontwo.core.data.source.remote

import android.content.Context
import android.util.Log
import com.mycapstoneprojectsubmissiontwo.core.R
import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.network.ApiResponse
import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.network.ApiService
import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.response.MovieResponse
import com.mycapstoneprojectsubmissiontwo.core.di.CoreScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

@CoreScope
class RemoteDataSource @Inject constructor(private val context: Context, private val apiService: ApiService){
    suspend fun getAllMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getTrendingMovie(context.resources.getString(R.string.tmdb_api_key))
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(movieList))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getDetailMovie(movieId, context.resources.getString(R.string.tmdb_api_key))
                val movieList = ArrayList<MovieResponse>()
                movieList.add(response)
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(movieList))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e:Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchMovies(movieName: String): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getSearchMovie(context.resources.getString(R.string.tmdb_api_key), movieName)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(movieList))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}