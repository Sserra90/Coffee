package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.onViewById
import com.sserra.coffee.onViewWithMatcher
import org.hamcrest.Matcher

class ImageButtonCoffeeView(viewInteraction: ViewInteraction) : ImageCoffeeView(viewInteraction) {
    constructor(id: Int) : this(onViewById(id))
    constructor(matcher: Matcher<View>) : this(onViewWithMatcher(matcher))

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

open class ImageCoffeeView(viewInteraction: ViewInteraction) : BaseCoffeeView<ImageCoffeeView>(viewInteraction) {

    constructor(id: Int) : this(onViewById(id))
    constructor(matcher: Matcher<View>) : this(onViewWithMatcher(matcher))

    val scaleType: ImageCoffeeView
        get() = apply {
            check = Check.ScaleType
        }

}