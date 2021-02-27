package com.mycapstoneprojectsubmissiontwo.core.data

import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class SearchNetworkBoundResource<ResultType, RequestType> {
    private var searchResult: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        safeDeleteSearchResult(dbSource)

        when(val apiResponse = createCall().first()){
            is ApiResponse.Success -> {
                val allDbSource = getAllMovies().first()
                saveCallResult(apiResponse.data, allDbSource)
                emitAll(loadFromDB().map { Resource.Success(it) })
            }
            is ApiResponse.Empty -> {
                emitAll(loadFromDB().map { Resource.Success(it) })
            }
            is ApiResponse.Error -> {
                emit(Resource.Error<ResultType>(apiResponse.errorMessage))
            }
        }
    }

    protected abstract fun safeDeleteSearchResult(data: ResultType)
    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>
    protected abstract suspend fun saveCallResult(dataResponse: RequestType, data: ResultType)
    protected abstract fun loadFromDB(): Flow<ResultType>
    protected abstract fun getAllMovies(): Flow<ResultType>
    fun asFlow(): Flow<Resource<ResultType>> = searchResult
}