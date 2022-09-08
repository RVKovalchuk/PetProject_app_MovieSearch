package com.example.moviesearch.domain

import com.example.moviesearch.data.MainRepository
import com.example.moviesearch.data.api.ApiConstants.API_KEY
import com.example.moviesearch.data.api.TmbdApi
import com.example.moviesearch.data.api.TmbdResultsDto
import com.example.moviesearch.data.entity.Film
import com.example.moviesearch.utils.ConverterFromTmdbToFilm
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(
    private val repository: MainRepository,
    private val service: TmbdApi
) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        progressBarState.onNext(true)
        service.getFilmsInfo(API_KEY, "ru-RU", page)
            .enqueue(object :
                Callback<TmbdResultsDto> {
                override fun onResponse(
                    call: Call<TmbdResultsDto>,
                    response: Response<TmbdResultsDto>
                ) {
                    val list =
                        ConverterFromTmdbToFilm.convertFromTmbdToFilm(response.body()?.results)
                    Completable.fromSingle<List<Film>> {
                        repository.putToDb(list)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                    progressBarState.onNext(false)
                }

                override fun onFailure(call: Call<TmbdResultsDto>, t: Throwable) {
                    progressBarState.onNext(false)
                }
            })
    }

    fun getFilmsFromDB(): Observable<List<Film>> = repository.getAllFilmsFromDb()
}