package com.example.moviesearch.view.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.data.api.ApiConstants
import com.example.moviesearch.domain.Film
import com.example.moviesearch.view.customviews.RatingDonutView

class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.findViewById<TextView>(R.id.film_item_title)
    private val description = itemView.findViewById<TextView>(R.id.film_item_description)
    private val poster = itemView.findViewById<ImageView>(R.id.film_item_poster)
    private val ratingDonut = itemView.findViewById<RatingDonutView>(R.id.film_item_rating)

    fun bind(film: Film) {
        title.text = film.title

        Glide.with(itemView)
            .load(ApiConstants.IMAGE_URL + "w342" + film.poster)
            .centerCrop()
            .into(poster)

        description.text = film.description
        ratingDonut.rating = film.rating
    }
}