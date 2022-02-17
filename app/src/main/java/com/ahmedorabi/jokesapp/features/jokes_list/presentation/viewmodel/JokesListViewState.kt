package com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel

import com.ahmedorabi.jokesapp.domain.JokesResponse


sealed class JokesListViewState {

    object Idle : JokesListViewState()
    object Loading : JokesListViewState()
    data class SuccessResponse(val data: JokesResponse) : JokesListViewState()
    data class Error(val errorMsg: String = "") : JokesListViewState()


}
