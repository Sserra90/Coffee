package com.sserra.app

import android.view.Gravity
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
class RelativeViewTest {

    class RelativePage : Page<RelativePage>() {
        val relativeView: RelativeLayoutCoffeeView = RelativeLayoutCoffeeView(R.id.layout)
    }

    @get:Rule
    val activityRule = intentRule<ScrollActivity>()

    @Before
    fun before() {
        activityRule.launchActivity(null)
    }

    @Test
    fun testRelativeLayout() {
        val page = RelativePage()
        page {
            relativeView {
                isVisible
                layoutGravity shouldBe Gravity.CENTER
                gravity shouldBe Gravity.CENTER
            }
        }
    }
}
