package com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel

sealed class JokesListEvents {
    object GetAllJokes : JokesListEvents()
}