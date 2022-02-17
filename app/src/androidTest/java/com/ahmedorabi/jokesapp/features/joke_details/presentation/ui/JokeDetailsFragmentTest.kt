package com.ahmedorabi.jokesapp.features.joke_details.presentation.ui

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ahmedorabi.jokesapp.R
import com.ahmedorabi.jokesapp.domain.Joke
import com.ahmedorabi.jokesapp.features.HomeActivity
import com.ahmedorabi.jokesapp.launchFragmentInHiltContainer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class JokeDetailsFragmentTest {


    @get:Rule
    val activityTestRule = ActivityScenarioRule(HomeActivity::class.java)


    @Test
    fun isJokeDetailsFragmentDisplayed() {

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

        val bundle = Bundle()
        bundle.putParcelable("joke_item", joke)


        launchFragmentInHiltContainer<JokeDetailsFragment>(bundle)

        onView(withId(R.id.jokeDetailsId))
            .check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.jokeIdTV)).check(matches((withText("ID : " + joke.id))))
        onView(withId(R.id.jokeTypeTV)).check(matches((withText("Type : " + joke.type))))
        onView(withId(R.id.jokeSetupTV)).check(matches((withText("Setup : " + joke.setup))))
        onView(withId(R.id.jokePunchlineTV)).check(matches((withText("Punchline : " + joke.joke))))


    }

}