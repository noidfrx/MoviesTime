package com.example.moviestime.ui.movie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestime.application.AppConstants.BASE_IMAGE
import com.example.moviestime.core.BaseViewHolder
import com.example.moviestime.data.model.Movie
import com.example.moviestime.data.model.MovieList
import com.example.moviestime.databinding.MovieItemBinding

class MovieAdapter(
    private val movieList: List<Movie>,
    private val itemClickedListener: OnMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener {
        fun onMovieClicked(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MoviesViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            //Toma la posicion y la devuelve en caso que no sea -1
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener //Si no puede obtenerlo retorna onclick

            itemClickedListener.onMovieClicked(movieList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MoviesViewHolder -> holder.bind(movieList[position])
        }
    }

    override fun getItemCount(): Int = movieList.size

    private inner class MoviesViewHolder(
        val binding: MovieItemBinding,
        val context: Context
    ) : BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie) {
            Glide.with(context).load(BASE_IMAGE + item.poster_path).centerCrop()
                .into(binding.ivMovie)
        }

    }
}