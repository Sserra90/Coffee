package com.sserra.coffee.coffeviews

import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.onViewById

open class ViewGroupCoffeeView<T>(viewInteraction: ViewInteraction) : BaseCoffeeView<T>(viewInteraction) {
    constructor(id: Int) : this(onViewById(id))

    val layoutGravity: ViewGroupCoffeeView<T>
        get() = apply {
            check = Check.LayoutGravity
        }
}

class LinearLayoutCoffeeView(viewInteraction: ViewInteraction) : ViewGroupCoffeeView<LinearLayoutCoffeeView>(viewInteraction) {
    constructor(id: Int) : this(onViewById(id))

    val orientation: LinearLayoutCoffeeView
        get() = apply {
            check = Check.Orientation
        }

    val gravity: LinearLayoutCoffeeView
        get() = apply {
            check = Check.Gravity
        }
}

class RelativeLayoutCoffeeView(viewInteraction: ViewInteraction) : ViewGroupCoffeeView<RelativeLayoutCoffeeView>(viewInteraction) {
    constructor(id: Int) : this(onViewById(id))

    val gravity: RelativeLayoutCoffeeView
        get() = apply {
            check = Check.Gravity
        }
}