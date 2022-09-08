package com.example.moviesearch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviesearch.App
import com.example.moviesearch.data.entity.Film
import com.example.moviesearch.domain.Interactor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    @Inject
    lateinit var interactor: Interactor

    val filmsListData: Observable<List<Film>>
    val showProgressBar : BehaviorSubject<Boolean>

    init {
        App.instance.dagger.inject(this)
        showProgressBar = interactor.progressBarState
        filmsListData = interactor.getFilmsFromDB()
        getFilms()
    }

    private fun getFilms() {
        interactor.getFilmsFromApi(1)
    }

}