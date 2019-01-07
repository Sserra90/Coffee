package com.sserra.coffee.coffeviews

import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.onViewById

class ButtonCoffeeView(
        viewInteraction: ViewInteraction,
        block: ButtonCoffeeView.() -> Unit = {}
) : TextViewCoffeeView(viewInteraction) {

    init {
        block()
    }

    constructor(id: Int, block: ButtonCoffeeView.() -> Unit = {}) : this(onViewById(id), block)
}
