package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.onViewById
import com.sserra.coffee.onViewWithMatcher
import org.hamcrest.Matcher

class ButtonCoffeeView(viewInteraction: ViewInteraction) : AbsTextCoffeeView<ButtonCoffeeView>(viewInteraction) {
    constructor(id: Int) : this(onViewById(id))
    constructor(matcher: Matcher<View>) : this(onViewWithMatcher(matcher))
}
