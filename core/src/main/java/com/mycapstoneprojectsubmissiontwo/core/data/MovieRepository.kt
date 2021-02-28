package com.mycapstoneprojectsubmissiontwo.core.data

import com.mycapstoneprojectsubmissiontwo.core.data.source.local.LocalDataSource
import com.mycapstoneprojectsubmissiontwo.core.data.source.local.entity.MovieEntity
import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.RemoteDataSource
import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.network.ApiResponse
import com.mycapstoneprojectsubmissiontwo.core.data.source.remote.response.MovieResponse
import com.mycapstoneprojectsubmissiontwo.core.di.CoreScope
import com.mycapstoneprojectsubmissiontwo.core.domain.model.GenreData
import com.mycapstoneprojectsubmissiontwo.core.domain.model.MovieData
import com.mycapstoneprojectsubmissiontwo.core.domain.repository.IMovieRepository
import com.mycapstoneprojectsubmissiontwo.core.utils.AppExecutors
import com.mycapstoneprojectsubmissiontwo.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@CoreScope
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IMovieRepository{
    override fun getAllMovies(): Flow<Resource<List<MovieData>>> =
        object : NetworkBoundResource<List<MovieData>, List<MovieResponse>>() {
            override fun loadFromDBFirst(): Flow<List<MovieData>> {
                return localDataSource.getTodayMovies().map { DataMapper.mapMovieEntitiesToDomains(it) }
            }

            override fun loadFromDB(): Flow<List<MovieData>> {
                return localDataSource.getAllMovies().map { DataMapper.mapMovieEntitiesToDomains(it) }
            }

            override fun shouldFetch(data: List<MovieData>?): Boolean {
                var isFetch = true
                if (data != null) {
                    if (data.isNotEmpty()) {
                        if (data[0].movieDate == DataMapper.getTodayDate()) {
                            isFetch = false
                        } else {
                            appExecutors.diskIO().execute { localDataSource.deleteAllTodayGames(data[0].movieDate) }
                        }
                    }
                }
                return isFetch
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> = remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun getSearchMovies(movieName: String): Flow<Resource<List<MovieData>>> =
        object : SearchNetworkBoundResource<List<MovieData>, List<MovieResponse>>() {
            override fun safeDeleteSearchResult(data: List<MovieData>) {
                val movieList = ArrayList<MovieData>()
                for (movie in data) {
                    movie.movieIsVisited = false
                    movie.movieIsSearchResult = false
                    movieList.add(movie)
                }
                val movieListEntity = DataMapper.mapMovieDomainsToEntities(movieList)
                appExecutors.diskIO().execute { localDataSource.setUpdateMovies(movieListEntity) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> = remoteDataSource.getSearchMovies(movieName)

            override suspend fun saveCallResult(dataResponse: List<MovieResponse>, data: List<MovieData>) {
                val updateMovieList = ArrayList<MovieEntity>()
                val insertMovieList = ArrayList<MovieEntity>()
                dataResponse.map {
                    var isNotFound = true
                    for (oldMovieData in data) {
                        if (it.id == oldMovieData.movieId) {
                            val newUpdateMovieEntity = DataMapper.mapSearchUpdateMovieResponseToEntity(it, oldMovieData)
                            updateMovieList.add(newUpdateMovieEntity)
                            isNotFound = false
                        }
                    }
                    if (isNotFound) {
                        val newInsertMovieEntity = DataMapper.mapSearchInsertMovieResponseToEntity(it)
                        insertMovieList.add(newInsertMovieEntity)
                    }
                }
                localDataSource.insertMovie(updateMovieList)
                localDataSource.insertMovie(insertMovieList)
            }

            override fun loadFromDB(): Flow<List<MovieData>> {
                return localDataSource.getSearchAllMovies().map { DataMapper.mapMovieEntitiesToDomains(it) }
            }

            override fun getAllMovies(): Flow<List<MovieData>> {
                return localDataSource.getAllMoviesLiterally().map { DataMapper.mapMovieEntitiesToDomains(it) }
            }
        }.asFlow()

    override fun getMovieDetailByMovieId(movieId: Int): Flow<Resource<List<MovieData>>> =
        object : NetworkBoundResource<List<MovieData>, List<MovieResponse>>() {
            override fun loadFromDBFirst(): Flow<List<MovieData>> {
                return localDataSource.getMovieDetailByMovieId(movieId).map { DataMapper.mapMovieEntitiesToDomains(it) }
            }

            override fun loadFromDB(): Flow<List<MovieData>> {
                return localDataSource.getMovieDetailByMovieId(movieId).map { DataMapper.mapMovieEntitiesToDomains(it) }
            }

            override fun shouldFetch(data: List<MovieData>?): Boolean {
                var isFetch = true
                if (data != null) {
                    isFetch = data[0].movieIsVisited == false
                }
                return isFetch
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> = remoteDataSource.getMovieDetail(movieId)

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieEntities = DataMapper.mapMovieResponsesToEntities(data)
                for (movie in movieEntities) {
                    for (moviesData in data) {
                        val genreList = moviesData.genres
                        val genreEntitiesList = DataMapper.mapGenreResponsesToEntities(genreList, moviesData.id ?: 0)
                        localDataSource.insertGenres(genreEntitiesList)
                    }
                    appExecutors.diskIO().execute { localDataSource.setUpdateDetailMovie(movie) }
                }
            }
        }.asFlow()

    override fun getGenreByMovieId(movieId: Int): Flow<List<GenreData>> {
        return localDataSource.getMovieGenresByMovieId(movieId).map { DataMapper.mapGenreEntitiesToDomains(it) }
    }

    override fun getFavoriteMovie(): Flow<List<MovieData>> {
        return localDataSource.getFavoriteMovie().map { DataMapper.mapMovieEntitiesToDomains(it) }
    }

    override fun getSearchFavorite(movieName: String): Flow<List<MovieData>> {
        return localDataSource.getSearchFavorite(movieName).map { DataMapper.mapMovieEntitiesToDomains(it) }
    }

    override fun setFavoriteMovie(movieData: MovieData, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movieData)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }
}