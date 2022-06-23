package com.example.moviesearch.view.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.domain.Film
import kotlinx.android.synthetic.main.film_item.view.*

class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.film_item_title
    private val description = itemView.film_item_description
    private val poster = itemView.film_item_poster
    private val ratingDonut = itemView.film_item_rating

    fun bind(film: Film) {
        title.text = film.title

        Glide.with(itemView)
            .load(film.poster)
            .centerCrop()
            .into(poster)

        description.text = film.description

        ratingDonut.rating = film.rating
    }
}