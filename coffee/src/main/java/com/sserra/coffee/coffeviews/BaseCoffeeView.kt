package com.sserra.coffee.coffeviews

import android.view.View
import android.widget.ImageView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.sserra.coffee.*
import org.hamcrest.CoreMatchers.`is`
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
    object ToolbarSubTitle : Check()
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
    object Weight : Check()
    object Alpha : Check()
}

open class CoffeeView(matcher: Matcher<View>) : BaseCoffeeView<CoffeeView>(matcher) {
    constructor(id: Int) : this(withId(id))
}

abstract class BaseCoffeeView<T>(protected val matcher: Matcher<View>) : BaseViewMatchers(matcher) {

    protected var check: Check = Check.None
    protected val viewInteraction: ViewInteraction = onViewWithMatcher(matcher)

    constructor(id: Int) : this(ViewMatchers.withId(id))

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

    val weight: BaseCoffeeView<T>
        get() = apply {
            check = Check.Weight
        }

    val alpha: BaseCoffeeView<T>
        get() = apply {
            check = Check.Alpha
        }

    fun click(): BaseCoffeeView<T> = apply { viewInteraction.click() }

    infix fun BaseCoffeeView<T>.shouldBe(value: Float): BaseCoffeeView<T> = apply {
        when (check) {
            Check.TextSize -> viewInteraction.hasTextSize(value)
            Check.Elevation -> viewInteraction.hasElevation(value)
            Check.Weight -> viewInteraction.hasWeight(value)
            Check.Alpha -> viewInteraction.hasAlpha(value)
        }
    }

    infix fun BaseCoffeeView<T>.shouldBe(value: Int): BaseCoffeeView<T> = apply {
        when (check) {
            Check.ChildrenNr -> viewInteraction.hasChildrenNumber(value)
            Check.BackgroundColor -> viewInteraction.hasBackground(value)
            Check.ToolbarTitle -> viewInteraction.hasToolbarTitle(value)
            Check.ToolbarSubTitle -> viewInteraction.hasToolbarSubTitle(value)
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
            Check.ToolbarTitle -> viewInteraction.hasToolbarTitle(value)
            Check.ToolbarSubTitle -> viewInteraction.hasToolbarSubTitle(value)
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

abstract class BaseViewMatchers(private val parent: Matcher<View>) {
    fun byId(id: Int): Matcher<View> = allOf(
            ViewMatchers.isDescendantOfA(parent),
            ViewMatchers.withId(id)
    )

    fun byText(text: String): Matcher<View> = allOf(
            ViewMatchers.isDescendantOfA(parent),
            ViewMatchers.withText(text)
    )

    fun byTag(tag: String): Matcher<View> = allOf(
            ViewMatchers.isDescendantOfA(parent),
            ViewMatchers.withTagValue(`is`(tag))
    )

    fun byMatcher(matcher: Matcher<View>): Matcher<View> = allOf(
            ViewMatchers.isDescendantOfA(parent),
            matcher
    )
}