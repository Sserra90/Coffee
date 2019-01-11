package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.onViewWithMatcher
import org.hamcrest.Matcher

open class TextCoffeeView(viewInteraction: ViewInteraction) : AbsTextCoffeeView<TextCoffeeView>(viewInteraction) {
    constructor(matcher: Matcher<View>) : this(onViewWithMatcher(matcher))
}

open class AbsTextCoffeeView<T>(viewInteraction: ViewInteraction) : BaseCoffeeView<T>(viewInteraction) {

    val text: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.Text
        }

    val textColor: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.TextColor
        }

    val textSize: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.TextSize
        }

    val textStyle: AbsTextCoffeeView<T>
        get() = apply {}

    val gravity: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.Gravity
        }

}