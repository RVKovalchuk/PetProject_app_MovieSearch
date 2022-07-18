package com.example.moviesearch.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.*
import com.example.moviesearch.domain.Film
import com.example.moviesearch.utils.AnimationHelper
import com.example.moviesearch.view.MainActivity
import com.example.moviesearch.view.adapters.FilmListRecyclerAdapter
import com.example.moviesearch.view.adapters.TopSpacingItemDecoration
import com.example.moviesearch.viewmodel.HomeFragmentViewModel
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentRoot = view.findViewById<ConstraintLayout>(R.id.fragment_home_root)

        searchView(view)
        initRecyclerView()
        filmsAdapter.addItems(filmsDataBase)

        AnimationHelper.performFragmentCircularRevealAnimation(
            fragmentRoot,
            requireActivity(),
            4
        )

        viewModel.filmsListLiveData.observe(viewLifecycleOwner) {
            filmsDataBase = it
            filmsAdapter.addItems(it)
            print(it)
        }
    }

    private fun searchView(view: View) {
        val search =
            view.findViewById<androidx.appcompat.widget.SearchView>(R.id.fragment_home_search)
        search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return if (newText.isNullOrBlank()) {
                    filmsAdapter.addItems(filmsDataBase)
                    true
                } else {
                    val result = filmsDataBase.filter {
                        it.title.lowercase(
                            Locale.getDefault()
                        ).contains(newText.lowercase(Locale.getDefault()))
                    }
                    filmsAdapter.addItems(result)
                    true
                }
            }
        })
    }

    private fun initRecyclerView() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        filmsAdapter.addItems(filmsDataBase)
    }
}