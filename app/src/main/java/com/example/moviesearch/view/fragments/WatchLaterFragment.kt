package com.example.moviesearch.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.moviesearch.R
import com.example.moviesearch.utils.AnimationHelper


class WatchLaterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watch_later, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentRoot = view.findViewById<ConstraintLayout>(R.id.watch_later_fragment_root)
        AnimationHelper.performFragmentCircularRevealAnimation(
            fragmentRoot,
            requireActivity(),
            2
        )
    }

}