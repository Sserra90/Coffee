package com.sserra.coffee.coffeviews

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.rule.ActivityTestRule
import com.sserra.coffee.RecyclerViewItemCountAssertion
import com.sserra.coffee.machers.LayoutManager
import com.sserra.coffee.machers.RecyclerViewMatcher
import com.sserra.coffee.machers.withLayoutManager
import com.sserra.coffee.onViewById
import com.sserra.coffee.scrollRecyclerToPos

class RecyclerCoffeeView(id: Int, block: RecyclerCoffeeView.() -> Unit = {}) : CoffeeView(onViewById(id)) {

    private var activityRule: ActivityTestRule<*>? = null
    private val recyclerViewMatcher: RecyclerViewMatcher = RecyclerViewMatcher(id)

    constructor(
            rule: ActivityTestRule<*>,
            id: Int,
            block: RecyclerCoffeeView.() -> Unit = {}
    ) : this(id, block) {
        activityRule = rule
    }

    init {
        block()
    }

    val itemCount: RecyclerCoffeeView
        get() = apply {
            check = Check.ItemCount
        }

    val usingGridLayoutManager: RecyclerCoffeeView
        get() = apply {
            viewInteraction.check(ViewAssertions.matches(withLayoutManager(LayoutManager.GRID)))
        }

    val usingLinearLayoutManager: RecyclerCoffeeView
        get() = apply {
            viewInteraction.check(ViewAssertions.matches(withLayoutManager(LayoutManager.LINEAR)))
        }

    fun scrollToPos(pos: Int): RecyclerCoffeeView = apply { viewInteraction.scrollRecyclerToPos(pos) }

    fun scrollToTop(): RecyclerCoffeeView = apply {
        scrollToPos(0)
    }

    fun <T : CoffeeView> atPos(pos: Int, block: T.() -> Unit): T {
        @Suppress("UNCHECKED_CAST")
        return CoffeeView(
                Espresso.onView(recyclerViewMatcher.atPosition(pos)),
                block as (CoffeeView.() -> Unit)
        ) as T
    }
}