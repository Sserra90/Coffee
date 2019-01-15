package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.sserra.coffee.onViewById
import com.sserra.coffee.onViewWithMatcher
import org.hamcrest.Matcher

open class TextCoffeeView(matcher: Matcher<View>) : AbsTextCoffeeView<TextCoffeeView>(matcher) {
    constructor(id: Int) : this(withId(id))
}

open class AbsTextCoffeeView<T>(matcher: Matcher<View>) : BaseCoffeeView<T>(matcher) {

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

    val gravity: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.Gravity
        }

    val textAllCaps: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.TextAllCaps
        }

    val textStyle: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.TextStyle
        }

    val drawableStart: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.DrawableLeft
        }

    val drawableEnd: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.DrawableRight
        }

    val drawableTop: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.DrawableTop
        }

    val drawableBottom: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.DrawableBottom
        }

}