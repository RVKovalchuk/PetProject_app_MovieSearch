package com.example.core_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_impl.FilmImpl
import io.reactivex.rxjava3.core.Observable

//Интерфейс с имплементациями методов работы с БД
@Dao
interface FilmDataAccessObjectImpl {
    @Query(DaoConstants.QUERY_FILM_DAO)
    fun getCashedFilms(): Observable<List<FilmImpl>>

    @Query(DaoConstants.QUERY_FILM_DAO_WATCH_LATER)
    fun getWatchLaterFilms(): Observable<List<FilmImpl>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<FilmImpl>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(film: FilmImpl)

    object DaoConstants {
        const val QUERY_FILM_DAO = "SELECT * FROM cashed_films"
        const val QUERY_FILM_DAO_WATCH_LATER =
            "SELECT * FROM cashed_films WHERE watch_later_time <> 0"
    }
}