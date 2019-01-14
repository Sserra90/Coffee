package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.matcher.ViewMatchers
import com.sserra.coffee.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Assert.fail

class ScrollCoffeeView(
        viewInteraction: ViewInteraction,
        block: ItemsFactory.() -> Unit
) : BaseCoffeeView<ScrollCoffeeView>(viewInteraction) {

    val itemsFactory: ItemsFactory = ItemsFactory(block)

    constructor(id: Int, block: ItemsFactory.() -> Unit) : this(onViewById(id), block)
    constructor(matcher: Matcher<View>, block: ItemsFactory.() -> Unit) : this(onViewWithMatcher(matcher), block)

    inline fun <reified T : BaseCoffeeView<*>> scrollTo(id: Int, block: T.() -> Unit) {
        val factory = itemsFactory.items[T::class]
        if (factory == null) {
            fail("No view provided for ${T::class}")
        }

        @Suppress("PROTECTED_CALL_FROM_PUBLIC_INLINE")
        viewInteraction.scrollToViewWithId(id)
        val view = factory!!.invoke(ViewMatchers.withId(id)) as T
        block.invoke(view)
    }

    inline fun <reified T : BaseCoffeeView<*>> scrollToViewWithTag(tag: String, block: T.() -> Unit) {
        val factory = itemsFactory.items[T::class]
        if (factory == null) {
            fail("No view provided for ${T::class}")
        }

        @Suppress("PROTECTED_CALL_FROM_PUBLIC_INLINE")
        viewInteraction.scrollToViewWithTag(tag)
        val view = factory!!.invoke(ViewMatchers.withTagValue(Matchers.`is`(tag))) as T
        block.invoke(view)
    }

    inline fun <reified T : BaseCoffeeView<*>> scrollToViewWithText(value: String, block: T.() -> Unit) {
        val factory = itemsFactory.items[T::class]
        if (factory == null) {
            fail("No view provided for ${T::class}")
        }

        @Suppress("PROTECTED_CALL_FROM_PUBLIC_INLINE")
        viewInteraction.scrollToViewWithText(value)
        val view = factory!!.invoke(ViewMatchers.withText(value)) as T
        block.invoke(view)
    }

    fun scrollToBottom(): ScrollCoffeeView = apply { viewInteraction.perform(swipeUp()) }

    fun scrollToTop(): ScrollCoffeeView = apply { viewInteraction.perform(swipeDown()) }
}