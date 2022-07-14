package com.example.moviesearch.di.modules

import com.example.moviesearch.data.MainRepository
import com.example.moviesearch.data.api.TmbdApi
import com.example.moviesearch.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Provides
    @Singleton
    fun provideInteractor(repository: MainRepository, tmbdApi: TmbdApi) = Interactor(
        service = tmbdApi,
        repository = repository
    )
}