package com.ahmedorabi.jokesapp.features.jokes_list.framework

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedorabi.jokesapp.TestCoroutineRule
import com.ahmedorabi.jokesapp.data.api.ApiService
import com.ahmedorabi.jokesapp.data.api.ResultWrapper
import com.ahmedorabi.jokesapp.domain.Joke
import com.ahmedorabi.jokesapp.domain.JokesResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
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
            "male",
            null,
            11,
            "There are only 10 kinds of people in this world: those who know binary and those who don't.",
            "",
            false,
            "",
            "single"
        )
        val jokesResponse = JokesResponse(10, false, arrayListOf(joke))

        runBlocking {

            Mockito.doReturn(jokesResponse)
                .`when`(apiService)
                .getJokesResponseAsync()

            val response = apiJokesListDataSource.getJokesResponse().first()

            when (response) {
                is ResultWrapper.Success -> {
                    val result = response.value
                    Assert.assertEquals(result.jokes[0].category, "Programming")
                }
            }
        }
    }


    @Test
    fun shouldGetListJokesFailureResponse() {

        runBlocking {

            given(apiService.getJokesResponseAsync()).willAnswer {
                throw IOException("Ooops")
            }

            val response = apiJokesListDataSource.getJokesResponse().first()

            MatcherAssert.assertThat(
                response,
                CoreMatchers.instanceOf(ResultWrapper.NetworkError::class.java)
            )

        }
    }
}