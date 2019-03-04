package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class ViewPagerCoffeeView(matcher: Matcher<View>) : BaseCoffeeView<ViewPagerCoffeeView>(matcher) {
    constructor(id: Int) : this(ViewMatchers.withId(id))

    fun swipeForward() {
        viewInteraction.perform(ViewActions.swipeRight())
    }

    fun swipeBakcward() {
        viewInteraction.perform(ViewActions.swipeLeft())
    }
}