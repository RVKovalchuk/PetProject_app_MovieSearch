package com.example.core_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_impl.FilmImpl
import io.reactivex.rxjava3.core.Observable

//Интерфейс с имплементациями методов работы с БД
@Dao
interface FilmDataAccessObjectImpl{
    @Query(DaoConstants.QUERY_FILM_DAO)
    fun getCashedFilms(): Observable<List<FilmImpl>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<FilmImpl>)

    object DaoConstants {
        const val QUERY_FILM_DAO = "SELECT * FROM cashed_films"
    }
}