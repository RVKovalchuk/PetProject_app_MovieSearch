package com.example.moviesearch.di

import android.content.Context
import com.example.moviesearch.di.modules.CoreModule
import com.example.moviesearch.di.modules.DomainModule
import com.example.moviesearch.viewmodel.HomeFragmentViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, CoreModule::class])

interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : AppComponent
    }

    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
}

