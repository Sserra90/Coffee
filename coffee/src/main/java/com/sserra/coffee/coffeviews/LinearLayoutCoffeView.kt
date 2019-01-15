package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher

open class ViewGroupCoffeeView<T>(matcher: Matcher<View>) : BaseCoffeeView<T>(matcher) {
    constructor(id: Int) : this(withId(id))

    val layoutGravity: ViewGroupCoffeeView<T>
        get() = apply {
            check = Check.LayoutGravity
        }
}

open class LinearLayoutCoffeeView(matcher: Matcher<View>) : ViewGroupCoffeeView<LinearLayoutCoffeeView>(matcher) {
    constructor(id: Int) : this(withId(id))

    val orientation: LinearLayoutCoffeeView
        get() = apply {
            check = Check.Orientation
        }

    val gravity: LinearLayoutCoffeeView
        get() = apply {
            check = Check.Gravity
        }
}

open class RelativeLayoutCoffeeView(matcher: Matcher<View>) : ViewGroupCoffeeView<RelativeLayoutCoffeeView>(matcher) {
    constructor(id: Int) : this(withId(id))

    val gravity: RelativeLayoutCoffeeView
        get() = apply {
            check = Check.Gravity
        }
}