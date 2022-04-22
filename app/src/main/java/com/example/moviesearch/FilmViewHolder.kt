package com.example.moviesearch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.film_item.view.*

class FilmViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.film_item_title
    private val description = itemView.film_item_description
    private val poster = itemView.film_item_poster

    fun bind(film: Film) {
        title.text = film.title
        poster.setImageResource(film.poster)
        description.text = film.description
    }
}