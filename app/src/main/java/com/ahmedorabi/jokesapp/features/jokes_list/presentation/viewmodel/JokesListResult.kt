package com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel

import com.ahmedorabi.jokesapp.domain.JokesResponse

sealed class JokesListResult {

    object Idle : JokesListResult()
    object Loading : JokesListResult()
    data class SuccessResponse(val data: JokesResponse) : JokesListResult()
    data class Error(val errorMsg: String = "") : JokesListResult()
}