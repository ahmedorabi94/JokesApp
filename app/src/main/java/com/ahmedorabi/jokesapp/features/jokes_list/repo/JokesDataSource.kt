package com.ahmedorabi.jokesapp.features.jokes_list.repo

import com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel.JokesListResult
import kotlinx.coroutines.flow.Flow

interface JokesDataSource {

    suspend fun getJokesResponse(): Flow<JokesListResult>

}