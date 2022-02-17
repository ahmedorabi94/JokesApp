package com.ahmedorabi.jokesapp.features.jokes_list.usecases

import com.ahmedorabi.jokesapp.features.jokes_list.repo.JokesRepository
import javax.inject.Inject

class GetJokesUseCase @Inject constructor(private val jokesRepository: JokesRepository) {


    suspend operator fun invoke() = jokesRepository.getJokesResponse()

}