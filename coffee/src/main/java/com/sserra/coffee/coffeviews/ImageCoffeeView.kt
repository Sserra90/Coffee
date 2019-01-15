package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.sserra.coffee.onViewById
import com.sserra.coffee.onViewWithMatcher
import org.hamcrest.Matcher

open class ImageButtonCoffeeView(matcher: Matcher<View>) : ImageCoffeeView(matcher) {
    constructor(id: Int) : this(withId(id))

    val text: ImageButtonCoffeeView
        get() = apply {
            check = Check.Text
        }

    val textColor: ImageButtonCoffeeView
        get() = apply {
            check = Check.TextColor
        }

    val textSize: ImageButtonCoffeeView
        get() = apply {
            check = Check.TextSize
        }

}

open class ImageCoffeeView(matcher: Matcher<View>) : BaseCoffeeView<ImageCoffeeView>(matcher) {

    constructor(id: Int) : this(withId(id))

    val scaleType: ImageCoffeeView
        get() = apply {
            check = Check.ScaleType
        }

}