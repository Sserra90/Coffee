package com.sserra.app

import android.view.Gravity
import android.widget.LinearLayout.VERTICAL
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sserra.coffee.coffeepages.Page
import com.sserra.coffee.coffeepages.PageWithToolbar
import com.sserra.coffee.coffeviews.*
import com.sserra.coffee.intentRule
import com.sserra.coffee.onViewWithMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LinearViewTest {

    class LinearPage : Page<LinearPage>() {
        val linearLayout: LinearLayoutCoffeeView = LinearLayoutCoffeeView(R.id.layout)
    }

    @get:Rule
    val activityRule = intentRule<LinearLayoutActivity>()

    @Before
    fun before() {
        activityRule.launchActivity(null)
    }

    @Test
    fun testLinearPage() {
        val page = LinearPage()
        page {
            linearLayout {
                isVisible
                layoutGravity shouldBe Gravity.CENTER
                gravity shouldBe Gravity.CENTER
                orientation shouldBe VERTICAL
            }
        }
    }
}
