package com.example.core_api

import android.content.Context

//Интерфейс с методом получения контекста
interface ContextProviderInterface {
    fun getContext() : Context
}