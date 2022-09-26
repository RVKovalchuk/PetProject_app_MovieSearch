package com.example.moviesearch.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Film
import com.example.moviesearch.*
import com.example.moviesearch.utils.AnimationHelper
import com.example.moviesearch.utils.AutoDisposable
import com.example.moviesearch.utils.addTo
import com.example.moviesearch.view.MainActivity
import com.example.moviesearch.view.adapters.FilmListRecyclerAdapter
import com.example.moviesearch.view.adapters.TopSpacingItemDecoration
import com.example.moviesearch.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    private val autoDisposable = AutoDisposable()

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
        autoDisposable.bindTo(lifecycle)
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

        viewModel.filmsListData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                filmsAdapter.addItems(list = it)
                filmsDataBase = it
            }
            .addTo(autoDisposable = autoDisposable)
    }

    private fun searchView(view: View) {
        val search =
            view.findViewById<androidx.appcompat.widget.SearchView>(R.id.fragment_home_search)

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            search.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newText: String): Boolean {
                    subscriber.onNext(newText)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    filmsAdapter.items.clear()
                    subscriber.onNext(newText)
                    return false
                }
            })
        })
            .subscribeOn(Schedulers.io())
            .map {
                it.lowercase(Locale.getDefault()).trim()
            }
            .debounce(1, TimeUnit.SECONDS)
            .filter {
                viewModel.getFilms()
                it.isNotBlank()
            }
            .map{
                viewModel.getSearchResult(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    Toast.makeText(requireContext(), "onError position", Toast.LENGTH_SHORT).show()
                },
                onNext = {
                    it.subscribe ({
                        filmsAdapter.addItems(it)
                    }, {})

                })
            .addTo(autoDisposable)

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