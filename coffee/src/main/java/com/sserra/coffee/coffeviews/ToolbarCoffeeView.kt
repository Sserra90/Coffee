package com.sserra.coffee.coffeviews

import android.annotation.SuppressLint
import android.view.View
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.sserra.coffee.*
import org.hamcrest.Matcher


class ToolbarCoffeeView(matcher: Matcher<View>) : BaseCoffeeView<ToolbarCoffeeView>(matcher) {

    constructor(id: Int) : this(withId(id))

    val title: ToolbarCoffeeView
        get() = apply {
            check = Check.ToolbarTitle
        }

    val subtitle: ToolbarCoffeeView
        get() = apply {
            check = Check.ToolbarSubTitle
        }

    val backButtonIsVisible: ToolbarCoffeeView
        @SuppressLint("PrivateResource")
        get() = apply {
            onViewWithDescription(R.string.abc_action_bar_up_description).isDisplayed()
        }

    val clickBackButton: ToolbarCoffeeView
        @SuppressLint("PrivateResource")
        get() = apply {
            onViewWithDescription(R.string.abc_action_bar_up_description).click()
        }
}