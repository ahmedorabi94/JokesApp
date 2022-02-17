package com.ahmedorabi.jokesapp.features.jokes_list.di

import com.ahmedorabi.jokesapp.data.api.ApiService
import com.ahmedorabi.jokesapp.features.jokes_list.framework.ApiJokesListDataSource
import com.ahmedorabi.jokesapp.features.jokes_list.repo.JokesDataSource
import com.ahmedorabi.jokesapp.features.jokes_list.repo.JokesRepository
import com.ahmedorabi.jokesapp.features.jokes_list.usecases.GetJokesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JokesListModule {

    @Singleton
    @Provides
    fun provideInApiJokesListDataSource(apiService: ApiService): JokesDataSource {
        return ApiJokesListDataSource(apiService)
    }

    @Singleton
    @Provides
    fun provideJokesRepository(usersDataSource: JokesDataSource): JokesRepository {
        return JokesRepository(usersDataSource)
    }


    @Singleton
    @Provides
    fun provideUseCase(usersRepository: JokesRepository): GetJokesUseCase {
        return GetJokesUseCase(usersRepository)
    }


}