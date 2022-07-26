package com.example.moviesearch.data

import android.content.ContentValues
import android.database.Cursor
import com.example.moviesearch.data.db.DbConstants
import com.example.moviesearch.data.db.DbHelper
import com.example.moviesearch.domain.Film

class MainRepository(dbHelper: DbHelper) {
    private val sqlDb = dbHelper.readableDatabase
    private lateinit var cursor: Cursor

    fun putToDb(film: Film) {
        val content = ContentValues()
        content.apply {
            put(DbConstants.COLUMN_TITLE, film.title)
            put(DbConstants.COLUMN_POSTER, film.poster)
            put(DbConstants.COLUMN_DESCRIPTION, film.description)
            put(DbConstants.COLUMN_RATING, film.rating)
        }
        sqlDb.insert(DbConstants.TABLE_NAME, null, content)
    }

    fun getAllFilmsFromDb(): List<Film> {
        cursor = sqlDb.rawQuery(DbConstants.QUERY_FOR_ALL_FILMS_FROM_DB, null)
        val result = mutableListOf<Film>()
        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(1)
                val poster = cursor.getString(2)
                val description = cursor.getString(3)
                val rating = cursor.getDouble(4)
                result.add(
                    Film(
                        title = title,
                        poster = poster,
                        description = description,
                        isInFavorites = false,
                        rating = rating
                    )
                )
            } while (cursor.moveToNext())
        }
        return result
    }
}