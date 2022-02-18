package com.ahmedorabi.jokesapp.features.jokes_list.framework

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedorabi.jokesapp.TestCoroutineRule
import com.ahmedorabi.jokesapp.data.api.ApiService
import com.ahmedorabi.jokesapp.domain.Joke
import com.ahmedorabi.jokesapp.domain.JokesResponse
import com.ahmedorabi.jokesapp.features.jokes_list.presentation.viewmodel.JokesListResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ApiJokesListDataSourceTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Mock
    lateinit var apiService: ApiService


    private lateinit var apiJokesListDataSource: ApiJokesListDataSource


    @Before
    fun setup() {

        apiJokesListDataSource = ApiJokesListDataSource(apiService)

    }

    @Test
    fun shouldGetJokesSuccessResponse() {


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
        val result = JokesListResult.SuccessResponse(jokesResponse)

        runBlocking {

            Mockito.doReturn(jokesResponse)
                .`when`(apiService)
                .getJokesResponseAsync()

            val response = apiJokesListDataSource.getJokesResponse().drop(1).first()

            Assert.assertEquals(response, result)

        }
    }


    @Test
    fun shouldGetListJokesFailureResponse() {

        val result = JokesListResult.Error("NetworkError")

        runBlocking {

            given(apiService.getJokesResponseAsync()).willAnswer {
                throw IOException("Ooops")
            }

            val response = apiJokesListDataSource.getJokesResponse().drop(1).first()

            Assert.assertEquals(response, result)


        }
    }
}