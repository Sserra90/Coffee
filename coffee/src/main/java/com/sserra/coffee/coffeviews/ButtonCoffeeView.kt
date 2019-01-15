package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher

open class ButtonCoffeeView(matcher: Matcher<View>) : AbsTextCoffeeView<ButtonCoffeeView>(matcher) {
    constructor(id: Int) : this(withId(id))
}
