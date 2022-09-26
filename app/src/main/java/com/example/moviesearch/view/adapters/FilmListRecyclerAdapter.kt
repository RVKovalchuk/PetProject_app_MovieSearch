package com.example.moviesearch.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Film
import com.example.moviesearch.R
import com.example.moviesearch.view.viewholders.FilmViewHolder

class FilmListRecyclerAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val items = mutableListOf<Film>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        )
    }

    override fun getItemCount() = items.size

    fun addItems(list: List<Film>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun click(film: Film)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val container = holder.itemView.findViewById<CardView>(R.id.film_item_container)
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(items[position])
                container.setOnClickListener {
                    clickListener.click(items[position])
                }
            }
        }
    }
}