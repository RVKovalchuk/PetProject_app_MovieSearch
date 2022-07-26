package com.example.moviesearch.data.db

object DbConstants {
    const val DB_NAME = "films.db"
    const val DB_VERSION = 1

    const val TABLE_NAME = "films_table"
    const val COLUMN_ID = "_id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_POSTER = "poster"
    const val COLUMN_DESCRIPTION = "description"
    const val COLUMN_RATING = "rating"

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_TITLE TEXT UNIQUE," +
            "$COLUMN_POSTER TEXT," +
            "$COLUMN_DESCRIPTION TEXT," +
            "$COLUMN_RATING REAL);"

    const val QUERY_FOR_ALL_FILMS_FROM_DB = "SELECT * FROM $TABLE_NAME"
}