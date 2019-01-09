package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import com.sserra.coffee.*
import org.hamcrest.Matcher

class ScrollCoffeeView(
        viewInteraction: ViewInteraction
) : CoffeeView<ScrollCoffeeView>(viewInteraction) {

    constructor(id: Int) : this(onViewById(id))
    constructor(matcher: Matcher<View>) : this(onViewWithMatcher(matcher))

    fun <T : CoffeeView<*>> scrollTo(id: Int, block: CoffeeView<*>.() -> Unit): T {
        viewInteraction.scrollToViewWithId(id)
        @Suppress("UNCHECKED_CAST")
        val v = CoffeeView<Any>(onViewById(id))
        block.invoke(v)
        return v as T
    }

    fun scrollToBottom(): ScrollCoffeeView = apply { viewInteraction.perform(swipeUp()) }

    fun scrollToTop(): ScrollCoffeeView = apply { viewInteraction.perform(swipeDown()) }
}