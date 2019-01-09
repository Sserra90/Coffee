package com.sserra.coffee.coffeviews

import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.onViewById

class ButtonCoffeeView(viewInteraction: ViewInteraction) : AbsTextCoffeeView<ButtonCoffeeView>(viewInteraction) {
    constructor(id: Int) : this(onViewById(id))
}
