package com.ahmedorabi.jokesapp.features.jokes_list.framework

import com.ahmedorabi.jokesapp.data.api.ApiService
import com.ahmedorabi.jokesapp.data.api.ResultWrapper
import com.ahmedorabi.jokesapp.data.api.safeApiCall
import com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel.JokesListResult
import com.ahmedorabi.jokesapp.features.jokes_list.repo.JokesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ApiJokesListDataSource @Inject constructor(private val apiService: ApiService) :
    JokesDataSource {


    override suspend fun getJokesResponse(): Flow<JokesListResult> {

        return flow {

            emit(JokesListResult.Loading)

            val response = safeApiCall(Dispatchers.IO) {
                apiService.getJokesResponseAsync()
            }

            when (response) {
                is ResultWrapper.Success -> {
                    emit(JokesListResult.SuccessResponse(response.value))
                }
                is ResultWrapper.Error -> {
                    emit(JokesListResult.Error(response.error?.message ?: "Unknown Error"))
                }
                is ResultWrapper.NetworkError -> {
                    emit(JokesListResult.Error("NetworkError"))

                }
                ResultWrapper.NoContentError -> {
                    emit(JokesListResult.Error("NoContentError"))

                }
            }

        }.flowOn(Dispatchers.IO)
    }


}