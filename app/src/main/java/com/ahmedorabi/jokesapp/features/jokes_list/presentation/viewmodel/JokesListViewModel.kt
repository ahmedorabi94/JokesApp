package com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedorabi.jokesapp.features.jokes_list.usecases.GetJokesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
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
                .collect { response ->
                    when (response) {
                        is JokesListResult.SuccessResponse -> {
                            _state.value = JokesListViewState.SuccessResponse(response.data)
                        }
                        is JokesListResult.Error -> {
                            _state.value =
                                JokesListViewState.Error(response.errorMsg)
                        }
                        is JokesListResult.Loading -> {
                            _state.value = JokesListViewState.Loading
                        }
                        JokesListResult.Idle -> {
                            _state.value = JokesListViewState.Idle
                        }
                    }
                }
        }

    }

}