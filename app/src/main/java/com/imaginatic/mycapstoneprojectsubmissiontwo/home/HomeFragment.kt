package com.imaginatic.mycapstoneprojectsubmissiontwo.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaginatic.mycapstoneprojectsubmissiontwo.MyApplication
import com.imaginatic.mycapstoneprojectsubmissiontwo.R
import com.imaginatic.mycapstoneprojectsubmissiontwo.databinding.FragmentHomeBinding
import com.imaginatic.mycapstoneprojectsubmissiontwo.detail.DetailMovieActivity
import com.mycapstoneprojectsubmissiontwo.core.data.Resource
import com.mycapstoneprojectsubmissiontwo.core.domain.model.MovieData
import com.mycapstoneprojectsubmissiontwo.core.ui.MovieAdapter
import com.mycapstoneprojectsubmissiontwo.core.ui.MovieAdapterClickListener
import com.mycapstoneprojectsubmissiontwo.core.ui.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment(), MovieAdapterClickListener {

    @Inject
    lateinit var factory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels {
        factory
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onMovieClickListener(movieData: MovieData) {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_DATA, movieData)
        startActivity(intent)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val movieAdapter = MovieAdapter(this)

            homeViewModel.movies.observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    when(movie) {
                        is Resource.Loading -> {
                            binding.rvMovies.visibility = View.GONE
                            binding.tvViewError.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            movieAdapter.setData(movie.data)
                            binding.progressBar.visibility = View.GONE
                            binding.tvViewError.visibility = View.GONE
                            binding.rvMovies.visibility = View.VISIBLE
                        }
                        is Resource.Error -> {
                            binding.tvViewError.text = movie.message
                            binding.progressBar.visibility = View.GONE
                            binding.rvMovies.visibility = View.GONE
                            binding.tvViewError.visibility = View.VISIBLE
                        }
                    }
                }
            })

            homeViewModel.searchMovies.observe(viewLifecycleOwner, { movieSearch ->
                if (movieSearch != null) {
                    when (movieSearch) {
                        is Resource.Loading -> {
                            binding.rvMovies.visibility = View.GONE
                            binding.tvViewError.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            if (!movieSearch.data.isNullOrEmpty()) {
                                movieAdapter.setData(movieSearch.data)
                                binding.tvViewError.visibility = View.GONE
                                binding.rvMovies.visibility = View.VISIBLE
                            } else {
                                binding.tvViewError.text = getString(R.string.movies_not_found)
                                binding.rvMovies.visibility = View.GONE
                                binding.tvViewError.visibility = View.VISIBLE
                            }
                        }
                        is Resource.Error -> {
                            binding.tvViewError.text = movieSearch.message
                            binding.rvMovies.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                            binding.tvViewError.visibility = View.VISIBLE
                        }
                    }
                }
            })

            with(binding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.browse_movies)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    homeViewModel.setMovieName(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }
}