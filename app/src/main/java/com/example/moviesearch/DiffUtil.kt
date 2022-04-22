package com.example.moviesearch

import androidx.recyclerview.widget.DiffUtil

class DiffUtil(private val oldList: List<Film>, private val newList: List<Film>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFilm = oldList[oldItemPosition]
        val newFilm = newList[newItemPosition]
        return oldFilm.title == newFilm.title && oldFilm.description == newFilm.description && oldFilm.poster == newFilm.poster
    }



}