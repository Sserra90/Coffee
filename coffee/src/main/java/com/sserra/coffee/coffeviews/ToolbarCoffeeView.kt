package com.sserra.coffee.coffeviews

import android.annotation.SuppressLint
import android.view.View
import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.*
import org.hamcrest.Matcher


class ToolbarCoffeeView(
        viewInteraction: ViewInteraction
) : BaseCoffeeView<ToolbarCoffeeView>(viewInteraction) {

    constructor(id: Int) : this(onViewById(id))
    constructor(matcher: Matcher<View>) : this(onViewWithMatcher(matcher))

    val title: ToolbarCoffeeView
        get() = apply {
            check = Check.ToolbarTitle
        }

    val subtitle: ToolbarCoffeeView
        get() = apply {
            check = Check.ToolbarTitle
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