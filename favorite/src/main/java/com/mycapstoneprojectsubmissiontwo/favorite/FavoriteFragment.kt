package com.mycapstoneprojectsubmissiontwo.favorite

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
import com.imaginatic.mycapstoneprojectsubmissiontwo.detail.DetailMovieActivity
import com.mycapstoneprojectsubmissiontwo.core.domain.model.MovieData
import com.mycapstoneprojectsubmissiontwo.core.ui.MovieAdapter
import com.mycapstoneprojectsubmissiontwo.core.ui.MovieAdapterClickListener
import com.mycapstoneprojectsubmissiontwo.favorite.databinding.FragmentFavoriteBinding
import javax.inject.Inject

class FavoriteFragment : Fragment(), MovieAdapterClickListener {

    @Inject
    lateinit var factory: ViewModelFavoriteFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    private lateinit var binding: FragmentFavoriteBinding

    override fun onMovieClickListener(movieData: MovieData) {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_DATA, movieData)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = (requireActivity().application as MyApplication).appComponent
        DaggerFavoriteComponent.builder().appComponent(appComponent).build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MovieAdapter(this)

        favoriteViewModel.favoriteMovies.observe(viewLifecycleOwner, { favoriteMovies ->
            movieAdapter.setData(favoriteMovies)
            if (favoriteMovies.isEmpty()) {
                binding.rvMoviesFavorite.visibility = View.GONE
                binding.noFavoriteFound.visibility = View.VISIBLE
            } else {
                binding.noFavoriteFound.visibility = View.GONE
                binding.rvMoviesFavorite.visibility = View.VISIBLE
            }
        })

        favoriteViewModel.searchFavoriteMovies.observe(viewLifecycleOwner, { searchFavoriteMovies ->
            movieAdapter.setData(searchFavoriteMovies)
            if (searchFavoriteMovies.isEmpty()) {
                binding.rvMoviesFavorite.visibility = View.GONE
                binding.noFavoriteFound.visibility = View.VISIBLE
            } else {
                binding.noFavoriteFound.visibility = View.GONE
                binding.rvMoviesFavorite.visibility = View.VISIBLE
            }
        })

        with(binding.rvMoviesFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.option_menu_favorite, menu)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search_favorite).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.browse_favorite_movies)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) { favoriteViewModel.setMovieName(query) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }
}