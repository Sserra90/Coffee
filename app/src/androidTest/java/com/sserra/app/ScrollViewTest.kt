package com.sserra.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sserra.app.ScrollActivity
import com.sserra.coffee.coffeepages.Page
import com.sserra.coffee.coffeviews.ScrollCoffeeView
import com.sserra.coffee.intentRule
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrollViewTest {

    class ScrollPage(block: ScrollPage.() -> Unit) : Page() {
        init {
            block()
        }

        fun scrollView(block: ScrollCoffeeView.() -> Unit): ScrollCoffeeView = ScrollCoffeeView(R.id.scrollView, block)
    }

    @get:Rule
    val activityRule = intentRule<ScrollActivity>()

    @Before
    fun before() {
        activityRule.launchActivity(null)
    }

    @Test
    fun canScroll() {
        ScrollPage {
            scrollView {
                isVisible
                scrollTo(R.id.card5)
            }
        }
    }
}
