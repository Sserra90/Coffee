package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.sserra.coffee.nestedScrollToAction
import com.sserra.coffee.waitFor
import org.hamcrest.Matcher

open class EditTextCoffeeView(matcher: Matcher<View>) : AbsTextCoffeeView<TextCoffeeView>(matcher) {
    constructor(id: Int) : this(withId(id))

    fun write(value: String, scroll: Boolean = false, wait: Long = 1000) {
        if (scroll) {
            viewInteraction.perform(nestedScrollToAction(), replaceText(value), closeSoftKeyboard())
        } else {
            viewInteraction.perform(replaceText(value), closeSoftKeyboard())
        }
        onView(isRoot()).perform(waitFor(wait))
    }
}
