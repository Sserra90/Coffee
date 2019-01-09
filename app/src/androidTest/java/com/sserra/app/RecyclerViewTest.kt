package com.sserra.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sserra.coffee.coffeepages.Page
import com.sserra.coffee.coffeviews.CoffeeView
import com.sserra.coffee.coffeviews.RecyclerCoffeeView
import com.sserra.coffee.coffeviews.TextCoffeeView
import com.sserra.coffee.intentRule
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {

    class RecyclerPage(block: RecyclerPage.() -> Unit) : Page() {
        init {
            block()
        }

        fun recyclerView(block: RecyclerCoffeeView.() -> Unit): RecyclerCoffeeView = RecyclerCoffeeView(R.id.list, block)
    }

    @get:Rule
    val activityRule = intentRule<RecyclerViewActivity>()

    @Before
    fun before() {
        activityRule.launchActivity(null)
    }

    @Test
    fun recyclerViewTest() {
        RecyclerPage {

            recyclerView {
                isVisible
                usingLinearLayoutManager
                itemCount shouldBe 50

                //scrollToPos(25)
                atPos<CoffeeView>(0) {
                    isClickable
                }
            }
        }
    }
}
