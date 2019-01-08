package com.sserra.coffee.coffeviews

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import com.sserra.coffee.*

class ScrollCoffeeView(
        viewInteraction: ViewInteraction,
        block: ScrollCoffeeView.() -> Unit = {}
) : CoffeeView(viewInteraction) {

    init {
        block()
    }

    constructor(id: Int, block: ScrollCoffeeView.() -> Unit = {}) : this(onViewById(id), block)

    fun <T : CoffeeView> scrollToViewWithText(text: String, block: T.() -> Unit): T {
        viewInteraction.scrollToViewWithText(text)
        @Suppress("UNCHECKED_CAST")
        return CoffeeView(onViewWithText(text), block as (CoffeeView.() -> Unit)) as T
    }

    fun <T : CoffeeView> scrollToViewWithTag(tag: String, block: T.() -> Unit): T {
        viewInteraction.scrollToViewWithTag(tag)
        @Suppress("UNCHECKED_CAST")
        return CoffeeView(onViewWithTag(tag), block as (CoffeeView.() -> Unit)) as T
    }

    fun <T : CoffeeView> scrollTo(id: Int, block: T.() -> Unit): T {
        viewInteraction.scrollToViewWithId(id)
        @Suppress("UNCHECKED_CAST")
        return CoffeeView(onViewById(id), block as (CoffeeView.() -> Unit)) as T
    }

    fun scrollToBottom(): ScrollCoffeeView = apply { viewInteraction.perform(swipeUp()) }

    fun scrollToTop(): ScrollCoffeeView = apply { viewInteraction.perform(swipeDown()) }
}