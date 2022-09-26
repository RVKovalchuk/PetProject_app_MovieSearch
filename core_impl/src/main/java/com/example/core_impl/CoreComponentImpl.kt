package com.example.core_impl

import android.content.Context
import com.example.core_api.CoreComponentInterface
import com.example.core_impl.module.FilmDatabaseModule
import com.example.core_impl.module.WebModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FilmDatabaseModule::class, WebModule::class])
abstract class CoreComponentImpl : CoreComponentInterface {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponentImpl
    }
}
