package com.ahmedorabi.jokesapp.features.jokes_list.presentation.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ahmedorabi.jokesapp.EspressoIdlingResourceRule
import com.ahmedorabi.jokesapp.R
import com.ahmedorabi.jokesapp.features.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class JokesListFragmentTest {

    @get: Rule
    val espressoIdlingResoureRule = EspressoIdlingResourceRule()

    @get:Rule
    val activityTestRule = ActivityScenarioRule(HomeActivity::class.java)


    @Test
    fun isHomeFragmentDis() {
        onView(withId(R.id.fragmentJokesListFragment)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view_main)).check(matches(isDisplayed()))
    }


    @Test
    fun isJokesListFragmentDisplayed() {

        onView(withId(R.id.recycler_view_main)).perform(click())
        onView(withId(R.id.jokeDetailsId)).check(matches(isDisplayed()))
        
    }
}