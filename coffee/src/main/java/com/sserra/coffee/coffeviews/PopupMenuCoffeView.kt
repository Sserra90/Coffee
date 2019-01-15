package com.sserra.coffee.coffeviews

import android.view.View
import androidx.appcompat.widget.MenuPopupWindow
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import com.sserra.coffee.clickOnMenuItem
import com.sserra.coffee.machers.withMenuItemTitle
import com.sserra.coffee.onViewWithMatcher
import org.hamcrest.Matcher

class PopupMenuCoffeeItem(private val matcher: Matcher<View>, private val pos: Int) {
    private var check: Check? = null

    operator fun invoke(function: PopupMenuCoffeeItem.() -> Unit) {
        function.invoke(this)
    }

    val title: PopupMenuCoffeeItem = apply {
        check = Check.Text
    }

    fun click() {
        onViewWithMatcher(matcher).perform(clickOnMenuItem(pos))
    }

    infix fun PopupMenuCoffeeItem.shouldBe(value: String): PopupMenuCoffeeItem = apply {
        when (check) {
            is Check.Text -> Espresso.onView(matcher).check(matches(withMenuItemTitle(pos, value)))
        }
    }

    infix fun PopupMenuCoffeeItem.shouldBe(value: Int): PopupMenuCoffeeItem = apply {
        when (check) {
            is Check.Text -> Espresso.onView(matcher).check(matches(withMenuItemTitle(pos, value)))
        }
    }
}

open class PopupMenuCoffeeView(matcher: Matcher<View>) : BaseCoffeeView<PopupMenuCoffeeView>(matcher) {

    constructor() : this(ViewMatchers.isAssignableFrom(MenuPopupWindow.MenuDropDownListView::class.java))

    val itemsNr: PopupMenuCoffeeView = apply {
        check = Check.ChildrenNr
    }

    fun itemAt(pos: Int, block: PopupMenuCoffeeItem.() -> Unit) {
        PopupMenuCoffeeItem(matcher, pos).invoke(block)
    }
}