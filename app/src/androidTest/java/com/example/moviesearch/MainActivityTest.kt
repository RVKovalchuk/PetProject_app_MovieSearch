package com.example.moviesearch

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviesearch.recyclerView.FilmViewHolder
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    //тест на доступность и кликабельность item'ов recycler view
    @Test
    fun recyclerViewShouldBeAttached() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FilmViewHolder>(
                0,
                click()
            )
        )
    }

    //тест на доступность и возможность ввода текста в search view
    @Test
    fun searchViewShouldBeAbleToInputText() {
        val text = "Text example"
        onView(withId(R.id.fragment_home_search)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_home_search)).perform(typeTextToSearchView(text))
    }

    //определяем новый Action View для SearchView
    private fun typeTextToSearchView(text: String?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return allOf(
                    isDisplayed(), isAssignableFrom(
                        SearchView::
                        class.java
                    )
                )
            }

            override fun getDescription(): String {
                return "type text to the search view"
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }

    //тест на проверку айтемов боттом бара
    @Test
    fun checkBottomBarItems() {
        onView(withId(R.id.selections)).perform(click())
        onView(withId(R.id.selection_fragment_root)).check(matches(isDisplayed()))

        onView(withId(R.id.watch_later)).perform(click())
        onView(withId(R.id.watch_later_fragment_root)).check(matches(isDisplayed()))

        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.favorite_fragment_root)).check(matches(isDisplayed()))

        onView(withId(R.id.home)).perform(click())
        onView(withId(R.id.fragment_home_root)).check(matches(isDisplayed()))
    }

    //тест на доступность фрагмента с деталями по клику
    @Test
    fun shouldOpenDetailsFragment() {
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<FilmViewHolder>(0, click()))
        onView(withId(R.id.bottom_app_bar)).check(matches(isDisplayed()))
    }

    //тест на кликабельность кнопки добавления в избранное
    @Test
    fun addToFavoriteButtonClickable() {
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<FilmViewHolder>(0, click()))
        onView(withId(R.id.fragment_details_fab_favorites)).perform(click())
        onView(withId(R.id.fragment_details_fab_favorites)).perform(click())
    }


}
