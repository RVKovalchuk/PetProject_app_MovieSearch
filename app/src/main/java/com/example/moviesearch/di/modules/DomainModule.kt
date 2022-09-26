package com.example.moviesearch.di.modules

import com.example.core_api.DbService
import com.example.core_api.WebService
import com.example.moviesearch.data.MainRepository
import com.example.moviesearch.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideMainRepo(dbService: DbService): MainRepository =
        MainRepository(dbService)

    @Provides
    @Singleton
    fun provideInteractor(repository: MainRepository, tmbdApi: WebService) = Interactor(
        service = tmbdApi,
        repository = repository
    )
}