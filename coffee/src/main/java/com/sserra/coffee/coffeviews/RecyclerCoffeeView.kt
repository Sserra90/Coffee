package com.sserra.coffee.coffeviews

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import com.sserra.coffee.*

class RecyclerCoffeeView(
        private val viewInteraction: ViewInteraction,
        block: RecyclerCoffeeView.() -> Unit = {}
) : CoffeeView(viewInteraction) {

    init {
        block()
    }

    constructor(id: Int, block: RecyclerCoffeeView.() -> Unit = {}) : this(onViewById(id), block)

    fun scrollToBottom(): RecyclerCoffeeView = apply { viewInteraction.perform(swipeUp()) }

    fun scrollToTop(): RecyclerCoffeeView = apply { viewInteraction.perform(swipeDown()) }
}