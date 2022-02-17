package com.ahmedorabi.jokesapp.features.jokes_list.framework

import com.ahmedorabi.jokesapp.data.api.ApiService
import com.ahmedorabi.jokesapp.data.api.ResultWrapper
import com.ahmedorabi.jokesapp.data.api.safeApiCall
import com.ahmedorabi.jokesapp.domain.JokesResponse
import com.ahmedorabi.jokesapp.features.jokes_list.repo.JokesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ApiJokesListDataSource @Inject constructor(private val apiService: ApiService) :
    JokesDataSource {


    override suspend fun getJokesResponse(): Flow<ResultWrapper<JokesResponse>> {

        return flow {

            val response = safeApiCall(Dispatchers.IO) {
                apiService.getJokesResponseAsync()
            }
            emit(response)

        }.flowOn(Dispatchers.IO)
    }


}