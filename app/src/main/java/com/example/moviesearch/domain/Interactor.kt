package com.example.moviesearch.domain

import com.example.moviesearch.data.MainRepository
import com.example.moviesearch.data.api.ApiConstants
import com.example.moviesearch.data.api.TmbdApi
import com.example.moviesearch.data.api.TmbdResultsDto
import com.example.moviesearch.utils.ConverterFromTmdbToFilm
import com.example.moviesearch.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repository: MainRepository, private val service: TmbdApi) {
    fun getFilmsDB(): List<Film> = repository.filmsDataBase

    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        service.getFilmsInfo(ApiConstants.API_KEY, "ru-RU", page).enqueue(object :
            Callback<TmbdResultsDto> {
            override fun onResponse(
                call: Call<TmbdResultsDto>,
                response: Response<TmbdResultsDto>
            ) {
                callback.onSuccess(ConverterFromTmdbToFilm.convertFromTmbdToFilm(response.body()?.tmbdFilmCards))
            }

            override fun onFailure(call: Call<TmbdResultsDto>, t: Throwable) {
                callback.onFailure()
            }

        })
    }
}