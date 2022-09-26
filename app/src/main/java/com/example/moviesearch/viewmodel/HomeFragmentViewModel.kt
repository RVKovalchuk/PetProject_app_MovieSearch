package com.example.moviesearch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.Film
import com.example.moviesearch.App
import com.example.moviesearch.domain.Interactor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    @Inject
    lateinit var interactor: Interactor

    val filmsListData: Observable<List<Film>>

    init {
        App.instance.dagger.inject(this)
        filmsListData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(1)
    }

    fun getSearchResult(search: String) = interactor.getSearchResultFromApi(search = search)

}