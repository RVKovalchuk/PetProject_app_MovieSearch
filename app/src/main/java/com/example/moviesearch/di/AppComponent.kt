package com.example.moviesearch.di

import com.example.moviesearch.di.modules.DatabaseModule
import com.example.moviesearch.di.modules.DomainModule
import com.example.moviesearch.di.modules.RemoteModule
import com.example.moviesearch.viewmodel.HomeFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RemoteModule::class, DatabaseModule::class, DomainModule::class])

interface AppComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
}