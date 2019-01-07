package com.sserra.coffee.coffeviews

import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.onViewById

open class TextViewCoffeeView(
        viewInteraction: ViewInteraction,
        block: TextViewCoffeeView.() -> Unit = {}
) : CoffeeView(viewInteraction) {

    init {
        block()
    }

    constructor(id: Int, block: TextViewCoffeeView.() -> Unit = {}) : this(onViewById(id), block)

    val text: TextViewCoffeeView
        get() = apply {
            check = Check.Text
        }

    val textColor: TextViewCoffeeView
        get() = apply {
            check = Check.TextColor
        }

    val textSize: TextViewCoffeeView
        get() = apply {
            check = Check.TextSize
        }

    val textStyle: TextViewCoffeeView
        get() = apply {}

}