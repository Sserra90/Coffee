package com.sserra.coffee.coffeviews

import android.view.View
import android.widget.ImageView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import com.sserra.coffee.*
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
    object Gravity : Check()
    object TextAllCaps : Check()
    object TextStyle : Check()
    object LayoutGravity : Check()
    object Orientation : Check()
    object ScaleType : Check()

    object DrawableLeft : Check()
    object DrawableTop : Check()
    object DrawableRight : Check()
    object DrawableBottom : Check()
}

open class CoffeeView(viewInteraction: ViewInteraction) : BaseCoffeeView<CoffeeView>(viewInteraction) {
    constructor(id: Int) : this(onViewById(id))
}

abstract class BaseCoffeeView<T>(protected val viewInteraction: ViewInteraction) {

    protected var check: Check = Check.None

    constructor(id: Int) : this(onViewById(id))

    operator fun invoke(function: T.() -> Unit) {
        @Suppress("UNCHECKED_CAST")
        function.invoke(this as T)
    }

    val isVisible: BaseCoffeeView<T>
        get() = apply { viewInteraction shouldBe VISIBLE }

    val isGone: BaseCoffeeView<T>
        get() = apply { viewInteraction shouldBe GONE }

    val childrenNr: BaseCoffeeView<T>
        get() = apply {
            check = Check.ChildrenNr
        }

    val backgroundColor: BaseCoffeeView<T>
        get() = apply {
            check = Check.BackgroundColor
        }

    val elevation: BaseCoffeeView<T>
        get() = apply {
            check = Check.Elevation
        }

    val isClickable: BaseCoffeeView<T>
        get() = apply {
            viewInteraction.isClickable()
        }

    val isNotClickable: BaseCoffeeView<T>
        get() = apply {
            viewInteraction.isNotClickable()
        }

    fun click(): BaseCoffeeView<T> = apply { viewInteraction.click() }

    infix fun BaseCoffeeView<T>.shouldBe(value: Float): BaseCoffeeView<T> = apply {
        when (check) {
            Check.TextSize -> viewInteraction.hasTextSize(value)
            Check.Elevation -> viewInteraction.hasElevation(value)
        }
    }

    infix fun BaseCoffeeView<T>.shouldBe(value: Int): BaseCoffeeView<T> = apply {
        when (check) {
            Check.ChildrenNr -> viewInteraction.hasChildrenNumber(value)
            Check.BackgroundColor -> viewInteraction.hasBackground(value)
            Check.ToolbarTitle -> viewInteraction.hasToolbarTitle(value)
            Check.Text -> viewInteraction.hasText(value)
            Check.TextColor -> viewInteraction.hasTextColor(value)
            Check.Elevation -> viewInteraction.hasElevation(value)
            Check.ItemCount -> viewInteraction.recyclerItemCount(value)
            Check.Orientation -> viewInteraction.hasOrientation(value)
            Check.Gravity -> viewInteraction.hasGravity(value)
            Check.TextStyle -> viewInteraction.hasTypeface(value)
            Check.DrawableRight -> viewInteraction.hasTextDrawable(value, DrawableDirection.END)
            Check.DrawableLeft -> viewInteraction.hasTextDrawable(value, DrawableDirection.START)
            Check.DrawableBottom -> viewInteraction.hasTextDrawable(value, DrawableDirection.BOTTOM)
            Check.DrawableTop -> viewInteraction.hasTextDrawable(value, DrawableDirection.TOP)
        }
    }

    infix fun BaseCoffeeView<T>.shouldBe(value: String): BaseCoffeeView<T> = apply {
        when (check) {
            Check.Text -> viewInteraction.hasText(value)
        }
    }

    infix fun BaseCoffeeView<T>.shouldBe(value: Boolean): BaseCoffeeView<T> = apply {
        when (check) {
            Check.TextAllCaps -> viewInteraction.hasTextAllCaps(value)
        }
    }

    infix fun BaseCoffeeView<T>.shouldBe(scaleType: ImageView.ScaleType): BaseCoffeeView<T> = apply {
        viewInteraction.hasScaleType(scaleType)
    }

}

abstract class AdapterCoffeeView<T>(private val parent: Matcher<View>) : BaseCoffeeView<T>(Espresso.onView(parent)) {
    fun withId(id: Int): Matcher<View> = allOf(
            ViewMatchers.isDescendantOfA(parent),
            ViewMatchers.withId(id)
    )

    fun withMatcher(matcher: Matcher<View>): Matcher<View> = allOf(
            ViewMatchers.isDescendantOfA(parent),
            matcher
    )
}
