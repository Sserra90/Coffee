package com.sserra.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sserra.coffee.coffeepages.Page
import com.sserra.coffee.coffeepages.PageWithToolbar
import com.sserra.coffee.coffeviews.BaseCoffeeView
import com.sserra.coffee.coffeviews.CoffeeView
import com.sserra.coffee.coffeviews.ScrollCoffeeView
import com.sserra.coffee.intentRule
import com.sserra.coffee.onViewWithMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrollViewTest {

    class ScrollPage : PageWithToolbar<ScrollPage>(1,1) {
        val scrollView = ScrollCoffeeView(R.id.scrollView) {
            add<BaseCoffeeView<*>> { CoffeeView(onViewWithMatcher(it)) }
        }
    }

    @get:Rule
    val activityRule = intentRule<ScrollActivity>()

    @Before
    fun before() {
        activityRule.launchActivity(null)
    }

    @Test
    fun canScroll() {
        val scrollPage = ScrollPage()
        scrollPage {

            scrollView {
                isVisible
                scrollTo<BaseCoffeeView<*>>(R.id.card5) {
                    click()
                }
                isOpen(ScrollActivity::class.java)
            }
        }
    }
}
