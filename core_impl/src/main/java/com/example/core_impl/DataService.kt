package com.example.core_impl

import android.content.Context
import androidx.room.Room
import com.example.core_api.DbService
import com.example.core_impl.db.FilmDatabase
import com.example.domain.Film
import io.reactivex.rxjava3.core.Observable

class DataService(context: Context) : DbService {
    private val roomDataAccessObject = Room.databaseBuilder(
        context,
        FilmDatabase::class.java,
        FilmImpl.Fields.TABLE_NAME_FOR_FILMS_DB
    ).build().filmDataAccessObject()

    override fun getCashedFilms(): Observable<List<Film>> {
        return roomDataAccessObject.getCashedFilms().map { it }
    }

    override fun insertAll(list: List<Film>) {
        roomDataAccessObject.insertAll(list.map { it.toImpl() })
    }
}