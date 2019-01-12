package com.sserra.app

import android.graphics.Typeface.ITALIC
import android.view.Gravity
import android.widget.ImageView.ScaleType.CENTER_CROP
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sserra.coffee.coffeepages.Page
import com.sserra.coffee.coffeviews.ImageCoffeeView
import com.sserra.coffee.coffeviews.RelativeLayoutCoffeeView
import com.sserra.coffee.coffeviews.TextCoffeeView
import com.sserra.coffee.intentRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RelativeViewTest {

    class RelativePage : Page<RelativePage>() {
        val relativeView: RelativeLayoutCoffeeView = RelativeLayoutCoffeeView(R.id.layout)
        val title: TextCoffeeView = TextCoffeeView(R.id.title)
        val image: ImageCoffeeView = ImageCoffeeView(R.id.image)
    }

    @get:Rule
    val activityRule = intentRule<RelativeLayoutActivity>()

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

                title {
                    isVisible
                    textAllCaps shouldBe true
                    textStyle shouldBe ITALIC
                    drawableStart shouldBe R.drawable.ic_launcher_background
                }

                image {
                    isVisible
                    scaleType shouldBe CENTER_CROP
                }
            }
        }
    }
}
