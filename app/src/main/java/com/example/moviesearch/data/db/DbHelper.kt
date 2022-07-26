package com.example.moviesearch.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DbConstants.DB_NAME, null, DbConstants.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbConstants.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}