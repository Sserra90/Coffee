package com.sserra.coffee.coffeviews

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import com.sserra.coffee.onViewById
import com.sserra.coffee.scrollTo

class ScrollCoffeeView(
        private val viewInteraction: ViewInteraction,
        block: ScrollCoffeeView.() -> Unit = {}
) : CoffeeView(viewInteraction) {

    init {
        block()
    }

    constructor(id: Int, block: ScrollCoffeeView.() -> Unit = {}) : this(onViewById(id), block)

    fun <T : CoffeeView> scrollTo(id: Int, block: T.() -> Unit): T {
        viewInteraction.scrollTo(id)
        @Suppress("UNCHECKED_CAST")
        return CoffeeView(onViewById(id), block as (CoffeeView.() -> Unit)) as T
    }

    fun scrollToBottom(): ScrollCoffeeView = apply { viewInteraction.perform(swipeUp()) }

    fun scrollToTop(): ScrollCoffeeView = apply { viewInteraction.perform(swipeDown()) }
}