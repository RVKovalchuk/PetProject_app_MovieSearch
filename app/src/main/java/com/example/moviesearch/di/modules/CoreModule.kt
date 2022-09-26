package com.example.moviesearch.di.modules

import android.content.Context
import com.example.core.CoreProvidesFactory
import com.example.core_api.CoreComponentInterface
import com.example.core_api.DbService
import com.example.core_api.WebService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Singleton
    @Provides
    fun provideWebService(coreComponentInterface: CoreComponentInterface): WebService = coreComponentInterface.getWebService()

    @Singleton
    @Provides
    fun provideDbService(coreComponentInterface: CoreComponentInterface): DbService = coreComponentInterface.getDbService()

    @Singleton
    @Provides
    fun provideCoreComponent(context: Context): CoreComponentInterface = CoreProvidesFactory.provideCoreComponent(context)
}