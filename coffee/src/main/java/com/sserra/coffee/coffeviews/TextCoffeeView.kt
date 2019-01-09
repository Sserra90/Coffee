package com.sserra.coffee.coffeviews

import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.onViewById

open class TextCoffeeView(
        viewInteraction: ViewInteraction
) : AbsTextCoffeeView<TextCoffeeView>(viewInteraction)

open class AbsTextCoffeeView<T>(viewInteraction: ViewInteraction) : CoffeeView<T>(viewInteraction) {

    constructor(id: Int) : this(onViewById(id))

    val text: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.Text
        }

    val textColor: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.TextColor
        }

    val textSize: AbsTextCoffeeView<T>
        get() = apply {
            check = Check.TextSize
        }

    val textStyle: AbsTextCoffeeView<T>
        get() = apply {}

}