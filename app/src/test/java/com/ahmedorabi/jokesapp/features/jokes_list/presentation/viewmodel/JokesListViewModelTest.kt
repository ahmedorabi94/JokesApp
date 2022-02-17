package com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedorabi.jokesapp.TestCoroutineRule
import com.ahmedorabi.jokesapp.data.api.ErrorResponse
import com.ahmedorabi.jokesapp.data.api.ResultWrapper
import com.ahmedorabi.jokesapp.domain.Joke
import com.ahmedorabi.jokesapp.domain.JokesResponse
import com.ahmedorabi.jokesapp.features.jokes_list.usecases.GetJokesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class JokesListViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    private lateinit var viewModel: JokesListViewModel

    @Mock
    private lateinit var useCase: GetJokesUseCase


    @Before
    fun setup() {
        viewModel = JokesListViewModel(useCase)
    }


    @Test
    fun givenResponseSuccess_getAllJokes_shouldReturnSuccess() {
        val joke = Joke(
            "Programming",
            "Parsing HTML with regex.",
            null,
            11,
            ".",
            "",
            false,
            "Hey, wanna hear a joke?",
            "twopart"
        )

        val jokesResponse = JokesResponse(10, false, arrayListOf(joke))

        val state = JokesListViewState.SuccessResponse(jokesResponse)

        val flow = flow {
            emit(
                ResultWrapper.Success(jokesResponse)
            )
        }

        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(flow)
                .`when`(useCase)
                .invoke()

            viewModel.jokeIntent.send(JokesListEvents.GetAllJokes)

            Mockito.verify(useCase).invoke()

            Assert.assertEquals(viewModel.state.value, state)

        }


    }

    @Test
    fun givenResponseError_getAllJokes_shouldReturnError() {

        val state = JokesListViewState.Error("404 Not Found")

        val flow = flow {
            emit(
                ResultWrapper.Error(404, ErrorResponse(message = "404 Not Found"))
            )
        }

        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(flow)
                .`when`(useCase)
                .invoke()

            viewModel.jokeIntent.send(JokesListEvents.GetAllJokes)

            Mockito.verify(useCase).invoke()

            Assert.assertEquals(viewModel.state.value, state)

        }


    }
}