package com.example.moviesearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.moviesearch.R
import com.example.moviesearch.utils.AnimationHelper


class FavoritesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentRoot = view.findViewById<ConstraintLayout>(R.id.favorite_fragment_root)
        AnimationHelper.performFragmentCircularRevealAnimation(
            fragmentRoot,
            requireActivity(),
            1
        )
    }
}