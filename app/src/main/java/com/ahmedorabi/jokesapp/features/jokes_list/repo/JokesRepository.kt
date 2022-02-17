package com.ahmedorabi.jokesapp.features.jokes_list.repo

import javax.inject.Inject

class JokesRepository @Inject constructor(private val dataSource: JokesDataSource) {

    suspend fun getJokesResponse() = dataSource.getJokesResponse()

}