package com.sserra.app

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sserra.coffee.coffeepages.Page
import com.sserra.coffee.coffeviews.CoffeeView
import com.sserra.coffee.coffeviews.RecyclerCoffeeView
import com.sserra.coffee.coffeviews.TextCoffeeView
import com.sserra.coffee.intentRule
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import kotlin.reflect.KClass

@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {

    class RecyclerPage : Page<RecyclerPage>() {
        val recyclerView: RecyclerCoffeeView
            get() {
                val items: Map<KClass<*>, (matcher: Matcher<View>) -> CoffeeView<*>> = mapOf(
                        CityView::class to { matcher -> CityView(matcher) }
                )
                return RecyclerCoffeeView(R.id.list, items)
            }
    }

    abstract class AdapterCoffeeView<T>(private val parent: Matcher<View>) : CoffeeView<T>(onView(parent)) {
        fun withId(id: Int): Matcher<View> = allOf(
                ViewMatchers.isDescendantOfA(parent),
                ViewMatchers.withId(id)
        )
        fun withMatcher(matcher: Matcher<View>): Matcher<View> = allOf(
                ViewMatchers.isDescendantOfA(parent),
                matcher
        )
    }

    class CityView(matcher: Matcher<View>) : AdapterCoffeeView<CityView>(matcher) {
        val title: TextCoffeeView = TextCoffeeView(withId(R.id.name))
    }

    @get:Rule
    val activityRule = intentRule<RecyclerViewActivity>()

    @Before
    fun before() {
        activityRule.launchActivity(null)
    }

    @Test
    fun recyclerViewTest() {
        val page = RecyclerPage()
        page {

            recyclerView {
                isVisible
                usingLinearLayoutManager
                itemCount shouldBe 50

                //scrollToPos(25)
                atPos<CityView>(0) {
                    isClickable
                    title {
                        text shouldBe "Albania"
                    }
                }
            }
        }
    }
}
