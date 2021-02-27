package com.mycapstoneprojectsubmissiontwo.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mycapstoneprojectsubmissiontwo.core.R
import com.mycapstoneprojectsubmissiontwo.core.databinding.ItemListMoviesBinding
import com.mycapstoneprojectsubmissiontwo.core.domain.model.MovieData
import com.mycapstoneprojectsubmissiontwo.core.utils.DataMapper

class MovieAdapter(private val listener: MovieAdapterClickListener): RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private var listData = ArrayList<MovieData>()

    fun setData(newListData: List<MovieData>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListMoviesBinding.bind(itemView)
        fun bind(data: MovieData) {
            with(itemView) {
                Glide.with(context)
                        .load(context.resources.getString(R.string.image_url_string, data.movieBackgroundImage))
                        .into(binding.ivItemImage)
                binding.tvItemTitle.text = data.movieTitle
                binding.tvItemReleaseDate.text = context.resources.getString(R.string.release_date_at, DataMapper.getDate(data.movieReleaseDate))
                binding.tvItemRating.text = context.resources.getString(R.string.rating_at, data.movieRating)
                binding.ivItemEighteenPlus.visibility = if (data.movieIsAdult) { View.VISIBLE } else { View.INVISIBLE }
                setOnClickListener {
                    listener.onMovieClickListener(listData[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_movies, parent, false))
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }
}