package com.example.moviesearch

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_details.*

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
        val film = arguments?.get("film") as Film
        val fragmentDetailsButtonFavorites =
            view?.findViewById<FloatingActionButton>(R.id.fragment_details_fab_favorites)
        val fragmentDetailsButtonShare =
            view?.findViewById<FloatingActionButton>(R.id.fragment_details_fab_share)

        activity_details_toolbar.title = film.title
        activity_details_poster.setImageResource(film.poster)
        activity_details_textview.text = film.description

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