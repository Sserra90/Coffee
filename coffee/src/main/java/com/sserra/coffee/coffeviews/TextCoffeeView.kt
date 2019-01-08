package com.sserra.coffee.coffeviews

import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.onViewById

open class TextCoffeeView(
        viewInteraction: ViewInteraction,
        block: TextCoffeeView.() -> Unit = {}
) : CoffeeView(viewInteraction) {

    init {
        block()
    }

    constructor(id: Int, block: TextCoffeeView.() -> Unit = {}) : this(onViewById(id), block)

    val text: TextCoffeeView
        get() = apply {
            check = Check.Text
        }

    val textColor: TextCoffeeView
        get() = apply {
            check = Check.TextColor
        }

    val textSize: TextCoffeeView
        get() = apply {
            check = Check.TextSize
        }

    val textStyle: TextCoffeeView
        get() = apply {}

}