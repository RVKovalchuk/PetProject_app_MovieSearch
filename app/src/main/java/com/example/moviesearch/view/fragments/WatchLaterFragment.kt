package com.example.moviesearch.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Film
import com.example.moviesearch.R
import com.example.moviesearch.utils.AnimationHelper
import com.example.moviesearch.utils.addTo
import com.example.moviesearch.view.MainActivity
import com.example.moviesearch.view.adapters.FilmListRecyclerAdapter
import com.example.moviesearch.view.adapters.TopSpacingItemDecoration
import com.example.moviesearch.viewmodel.HomeFragmentViewModel
import com.example.moviesearch.viewmodel.WatchLaterFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class WatchLaterFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(WatchLaterFragmentViewModel::class.java)
    }
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private var filmsDataBase = listOf<Film>()
        set(value) {
            if (field == value) return
            field = value
            filmsAdapter.addItems(field)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watch_later, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentRoot = view.findViewById<ConstraintLayout>(R.id.watch_later_fragment_root)
        initRecyclerView()
        AnimationHelper.performFragmentCircularRevealAnimation(
            fragmentRoot,
            requireActivity(),
            2
        )


        viewModel.filmsWatchLaterListData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(context, "${it}", Toast.LENGTH_SHORT).show()
                filmsAdapter.addItems(list = it)
                filmsDataBase = it
            }
    }

    private fun initRecyclerView() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        Toast.makeText(context, "${recyclerView == null}", Toast.LENGTH_SHORT).show()
        recyclerView?.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
            Toast.makeText(context, "Films adapter is init", Toast.LENGTH_SHORT).show()
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        filmsAdapter.addItems(filmsDataBase)
    }

}