package com.ahmedorabi.jokesapp.features.jokes_list.repo

import com.ahmedorabi.jokesapp.data.api.ResultWrapper
import com.ahmedorabi.jokesapp.domain.JokesResponse
import kotlinx.coroutines.flow.Flow

interface JokesDataSource {

    suspend fun getJokesResponse(): Flow<ResultWrapper<JokesResponse>>

}