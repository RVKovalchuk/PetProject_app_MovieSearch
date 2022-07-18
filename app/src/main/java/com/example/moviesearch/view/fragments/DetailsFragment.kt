package com.example.moviesearch.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.data.api.ApiConstants
import com.example.moviesearch.domain.Film
import com.example.moviesearch.domain.FilmConstants
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDetails()
    }

    private fun setDetails() {
        val film = arguments?.get(FilmConstants.FILM_BUNDLE_KEY) as Film
        val fragmentDetailsButtonFavorites =
            view?.findViewById<FloatingActionButton>(R.id.fragment_details_fab_favorites)
        val fragmentDetailsButtonShare =
            view?.findViewById<FloatingActionButton>(R.id.fragment_details_fab_share)
        val imageView = view?.findViewById<AppCompatImageView>(R.id.activity_details_poster)
        val toolbar = view?.findViewById<Toolbar>(R.id.activity_details_toolbar)
        val textView = view?.findViewById<TextView>(R.id.activity_details_textview)
        toolbar?.title = film.title

        if (imageView != null) {
            Glide.with(this)
                .load(ApiConstants.IMAGE_URL + "w780" + film.poster)
                .centerCrop()
                .into(imageView)
        }

        textView?.text = film.description

        fragmentDetailsButtonFavorites?.setImageResource(
            if (film.isInFavorites) R.drawable.ic_fragment_details_favorite_yes
            else R.drawable.ic_fragment_details_favorite_no
        )

        fragmentDetailsButtonFavorites?.setOnClickListener {
            if (!film.isInFavorites) {
                fragmentDetailsButtonFavorites.setImageResource(R.drawable.ic_fragment_details_favorite_yes)
                film.isInFavorites = true
            } else {
                fragmentDetailsButtonFavorites.setImageResource(R.drawable.ic_fragment_details_favorite_no)
                film.isInFavorites = false
            }
        }

        fragmentDetailsButtonShare?.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Делюсь описанием фильма: \n${film.title} \n${film.description}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Поделиться с: "))
        }
    }

}