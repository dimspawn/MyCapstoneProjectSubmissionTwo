package com.mycapstoneprojectsubmissiontwo.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycapstoneprojectsubmissiontwo.core.R
import com.mycapstoneprojectsubmissiontwo.core.databinding.ItemListGenresBinding
import com.mycapstoneprojectsubmissiontwo.core.domain.model.GenreData

class GenreAdapter: RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private var listGenre = ArrayList<GenreData>()

    fun setData(newListGenre: List<GenreData>?) {
        if (newListGenre == null) return
        listGenre.clear()
        listGenre.addAll(newListGenre)
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListGenresBinding.bind(itemView)
        fun bind(data: GenreData) {
            binding.tvItemGenre.text = data.genreName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
            GenreViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_genres, parent, false))

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val data = listGenre[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listGenre.size
}