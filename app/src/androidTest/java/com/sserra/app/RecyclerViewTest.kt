package com.sserra.app

import android.view.View
import androidx.test.runner.AndroidJUnit4
import com.sserra.coffee.coffeepages.Page
import com.sserra.coffee.coffeviews.*
import com.sserra.coffee.intentRule
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {

    class RecyclerPage : Page<RecyclerPage>() {
        val recyclerView: RecyclerCoffeeView = RecyclerCoffeeView(R.id.list) { add<CityView> { CityView(it) } }
    }

    class CityView(matcher: Matcher<View>) : BaseCoffeeView<CityView>(matcher) {
        val title: TextCoffeeView = TextCoffeeView(byId(R.id.name))
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
