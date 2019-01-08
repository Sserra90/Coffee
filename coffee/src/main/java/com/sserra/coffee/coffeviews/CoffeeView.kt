package com.sserra.coffee.coffeviews

import androidx.test.espresso.ViewInteraction
import com.sserra.coffee.*

const val VISIBLE = true
const val GONE = false

val <T> T.then: T
    get() = this

fun <T> T.then(block: T.() -> Unit): T {
    block()
    return this
}

sealed class Check {
    object None : Check()
    object ChildrenNr : Check()
    object BackgroundColor : Check()
    object ToolbarTitle : Check()
    object Elevation : Check()
    object TextColor : Check()
    object TextSize : Check()
    object Text : Check()
}

open class CoffeeView(
        protected val viewInteraction: ViewInteraction,
        block: CoffeeView.() -> Unit = {}
) {

    protected var check: Check = Check.None

    constructor(id: Int, block: CoffeeView.() -> Unit = {}) : this(onViewById(id), block)

    init {
        block()
    }

    val isVisible: CoffeeView
        get() = apply { viewInteraction shouldBe VISIBLE }

    val isGone: CoffeeView
        get() = apply { viewInteraction shouldBe GONE }

    val childrenNr: CoffeeView
        get() = apply {
            check = Check.ChildrenNr
        }

    val backgroundColor: CoffeeView
        get() = apply {
            check = Check.BackgroundColor
        }

    val elevation: CoffeeView
        get() = apply {
            check = Check.Elevation
        }

    val isClickable: CoffeeView
        get() = apply {
            viewInteraction.isClickable()
        }

    val isNotClickable: CoffeeView
        get() = apply {
            viewInteraction.isNotClickable()
        }

    fun click(): CoffeeView = apply { viewInteraction.click() }

    infix fun CoffeeView.shouldBe(value: Float): CoffeeView = apply {
        when (check) {
            Check.TextSize -> viewInteraction.hasTextSize(value)
            Check.Elevation -> viewInteraction.hasElevation(value)
        }
    }

    infix fun CoffeeView.shouldBe(value: Int): CoffeeView = apply {
        when (check) {
            Check.ChildrenNr -> viewInteraction.hasChildrenNumber(value)
            Check.BackgroundColor -> viewInteraction.hasBackground(value)
            Check.ToolbarTitle -> viewInteraction.hasToolbarTitle(value)
            Check.Text -> viewInteraction.hasText(value)
            Check.TextColor -> viewInteraction.hasTextColor(value)
            Check.Elevation -> viewInteraction.hasElevation(value)
        }
    }

    infix fun CoffeeView.shouldBe(value: String): CoffeeView = apply {
        when (check) {
            Check.Text -> viewInteraction.hasText(value)
        }
    }

}
