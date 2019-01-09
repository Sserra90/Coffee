package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.sserra.coffee.*
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

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
    object ItemCount : Check()
}

open class CoffeeView<T>(protected val viewInteraction: ViewInteraction) {

    protected var check: Check = Check.None

    constructor(id: Int) : this(onViewById(id))

    operator fun invoke(function: T.() -> Unit) {
        @Suppress("UNCHECKED_CAST")
        function.invoke(this as T)
    }

    val isVisible: CoffeeView<T>
        get() = apply { viewInteraction shouldBe VISIBLE }

    val isGone: CoffeeView<T>
        get() = apply { viewInteraction shouldBe GONE }

    val childrenNr: CoffeeView<T>
        get() = apply {
            check = Check.ChildrenNr
        }

    val backgroundColor: CoffeeView<T>
        get() = apply {
            check = Check.BackgroundColor
        }

    val elevation: CoffeeView<T>
        get() = apply {
            check = Check.Elevation
        }

    val isClickable: CoffeeView<T>
        get() = apply {
            viewInteraction.isClickable()
        }

    val isNotClickable: CoffeeView<T>
        get() = apply {
            viewInteraction.isNotClickable()
        }

    fun click(): CoffeeView<T> = apply { viewInteraction.click() }

    infix fun CoffeeView<T>.shouldBe(value: Float): CoffeeView<T> = apply {
        when (check) {
            Check.TextSize -> viewInteraction.hasTextSize(value)
            Check.Elevation -> viewInteraction.hasElevation(value)
        }
    }

    infix fun CoffeeView<T>.shouldBe(value: Int): CoffeeView<T> = apply {
        when (check) {
            Check.ChildrenNr -> viewInteraction.hasChildrenNumber(value)
            Check.BackgroundColor -> viewInteraction.hasBackground(value)
            Check.ToolbarTitle -> viewInteraction.hasToolbarTitle(value)
            Check.Text -> viewInteraction.hasText(value)
            Check.TextColor -> viewInteraction.hasTextColor(value)
            Check.Elevation -> viewInteraction.hasElevation(value)
            Check.ItemCount -> viewInteraction.recyclerItemCount(value)
        }
    }

    infix fun CoffeeView<T>.shouldBe(value: String): CoffeeView<T> = apply {
        when (check) {
            Check.Text -> viewInteraction.hasText(value)
        }
    }

}

abstract class AdapterCoffeeView<T>(private val parent: Matcher<View>) : CoffeeView<T>(Espresso.onView(parent)) {
    fun withId(id: Int): Matcher<View> = allOf(
            ViewMatchers.isDescendantOfA(parent),
            ViewMatchers.withId(id)
    )
    fun withMatcher(matcher: Matcher<View>): Matcher<View> = allOf(
            ViewMatchers.isDescendantOfA(parent),
            matcher
    )
}
