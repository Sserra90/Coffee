package com.sserra.coffee.coffeviews

import android.annotation.SuppressLint
import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.*


class ToolbarCoffeeView(
        viewInteraction: ViewInteraction,
        block: ToolbarCoffeeView.() -> Unit = {}
) : CoffeeView(viewInteraction) {

    init {
        block()
    }

    constructor(id: Int, block: ToolbarCoffeeView.() -> Unit = {}) : this(onViewById(id), block)

    val title: ToolbarCoffeeView
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