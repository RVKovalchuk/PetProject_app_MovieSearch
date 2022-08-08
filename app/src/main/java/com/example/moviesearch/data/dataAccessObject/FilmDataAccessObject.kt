package com.example.moviesearch.data.dataAccessObject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesearch.data.entity.Film

@Dao
interface FilmDataAccessObject {
    @Query(DataAccessObjectsConstants.QUERY_FILM_DAO)
    fun getCashedFilms(): List<Film>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)
}