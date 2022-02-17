package com.ahmedorabi.jokesapp.data.api

import com.ahmedorabi.jokesapp.domain.JokesResponse
import retrofit2.http.GET

interface ApiService {


    @GET("joke/Programming?type=twopart&amount=10")
    suspend fun getJokesResponseAsync(): JokesResponse


}