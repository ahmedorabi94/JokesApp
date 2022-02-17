package com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedorabi.jokesapp.data.api.ResultWrapper
import com.ahmedorabi.jokesapp.features.jokes_list.usecases.GetJokesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JokesListViewModel @Inject constructor(
    private val useCase: GetJokesUseCase,
) :
    ViewModel() {

    val jokeIntent = Channel<JokesListEvents>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<JokesListViewState>(JokesListViewState.Idle)
    val state: StateFlow<JokesListViewState>
        get() = _state


    init {
        handleEvents()
    }

    private fun handleEvents() {
        viewModelScope.launch {
            jokeIntent.consumeAsFlow().collect {
                when (it) {
                    is JokesListEvents.GetAllJokes -> {
                        getJokesResponseFlow()
                    }

                }
            }
        }
    }


    private fun getJokesResponseFlow() {


        viewModelScope.launch {
            useCase.invoke()
                .onStart {
                    _state.value = JokesListViewState.Loading

                }.catch { exception ->
                    _state.value = JokesListViewState.Error(exception.message ?: "Unknown Error")
                }.collect { response ->
                    when (response) {
                        is ResultWrapper.Success -> {
                            _state.value = JokesListViewState.SuccessResponse(response.value)
                        }
                        is ResultWrapper.Error -> {
                            _state.value =
                                JokesListViewState.Error(response.error?.message ?: "Unknown Error")
                        }
                        is ResultWrapper.NetworkError -> {
                            _state.value = JokesListViewState.Error("NetworkError")

                        }
                        ResultWrapper.NoContentError -> {
                            _state.value = JokesListViewState.Error("NoContentError")

                        }
                    }
                }
        }


    }

}