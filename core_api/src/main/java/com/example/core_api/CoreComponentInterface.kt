package com.example.core_api

//Интерфейс для возврата FilmDAO
interface CoreComponentInterface {
    fun getDbService() : DbService
    fun getWebService() : WebService
}