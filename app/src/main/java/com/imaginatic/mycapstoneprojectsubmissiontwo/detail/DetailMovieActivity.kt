package com.imaginatic.mycapstoneprojectsubmissiontwo.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.imaginatic.mycapstoneprojectsubmissiontwo.MyApplication
import com.imaginatic.mycapstoneprojectsubmissiontwo.R
import com.imaginatic.mycapstoneprojectsubmissiontwo.databinding.ActivityDetailMovieBinding
import com.mycapstoneprojectsubmissiontwo.core.data.Resource
import com.mycapstoneprojectsubmissiontwo.core.domain.model.MovieData
import com.mycapstoneprojectsubmissiontwo.core.ui.GenreAdapter
import com.mycapstoneprojectsubmissiontwo.core.ui.ViewModelFactory
import javax.inject.Inject

class DetailMovieActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val detailMoviesViewModel: DetailMoviesViewModel by viewModels {
        factory
    }

    private lateinit var binding: ActivityDetailMovieBinding

    companion object {
        const val EXTRA_DATA = "extra_movie_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDetailMovie.title = getString(R.string.app_name)
        val movieData = intent.getParcelableExtra<MovieData>(EXTRA_DATA)

        showDetailGame(movieData)
    }

    private fun showDetailGame(movieData: MovieData?) {
        movieData?.let {
            detailMoviesViewModel.setMovieId(it.movieId)

            detailMoviesViewModel.detailMovie.observe(this, { movies ->
                if (movies != null) {
                    when(movies) {
                        is Resource.Loading -> {
                            binding.content.llContentDetailMovie.visibility = View.GONE
                            binding.content.viewErrorDetailMovie.visibility = View.GONE
                            binding.content.progressBarDetailMovie.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.content.progressBarDetailMovie.visibility = View.GONE
                            if (!movies.data.isNullOrEmpty()) {
                                val movie = movies.data?.get(0) as MovieData
                                binding.content.tvTitleDetailMovie.text = movie.movieTitle
                                binding.content.tvDetailDescription.text = movie.movieDescription
                                Glide.with(this)
                                        .load(resources.getString(R.string.image_url_string_original, movie.movieLandscapeImage))
                                        .into(binding.ivDetailImage)
                                binding.content.rbDetailRating.stepSize = 0.5f
                                binding.content.rbDetailRating.max = 5
                                binding.content.rbDetailRating.rating = (movie.movieRating.toFloat() * 5) / 10
                                setUpFab(movie)
                                binding.content.viewErrorDetailMovie.visibility = View.GONE
                                binding.content.llContentDetailMovie.visibility = View.VISIBLE
                            } else {
                                binding.content.viewErrorDetailMovie.text = resources.getString(R.string.empty_text)
                                binding.content.llContentDetailMovie.visibility = View.GONE
                                binding.content.viewErrorDetailMovie.visibility = View.VISIBLE
                            }
                        }
                        is Resource.Error -> {
                            binding.content.viewErrorDetailMovie.text = resources.getString(R.string.oops_something_went_wrong)
                            binding.content.llContentDetailMovie.visibility = View.GONE
                            binding.content.progressBarDetailMovie.visibility = View.GONE
                            binding.content.viewErrorDetailMovie.visibility = View.VISIBLE
                        }
                    }
                }
            })

            detailMoviesViewModel.genreMovie.observe(this, { genresMovie ->
                val genreAdapter = GenreAdapter()
                genreAdapter.setData(genresMovie)

                with(binding.content.rvGenresDetailMovie) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = genreAdapter
                }
            })
        }
    }

    private fun setUpFab(movieData: MovieData) {
        var statusFavorite = movieData.movieIsFavorite
        setFavorite(statusFavorite)

        binding.fabDetailMovie.setOnClickListener {
            statusFavorite = !statusFavorite
            detailMoviesViewModel.setFavoriteMovie(movieData, statusFavorite)
            setFavorite(statusFavorite)
        }
    }

    private fun setFavorite(favorite: Boolean) {
        if (favorite) {
            binding.fabDetailMovie.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        } else {
            binding.fabDetailMovie.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite))
        }
    }
}