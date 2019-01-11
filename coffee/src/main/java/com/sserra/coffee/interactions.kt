package com.sserra.coffee

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.sserra.coffee.machers.*
import org.hamcrest.Matchers


infix fun ViewInteraction.hasChildThatMatch(value: String): ViewInteraction =
        check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(value)))).isDisplayed()

infix fun ViewInteraction.shouldBeRes(value: Int): ViewInteraction =
        isDisplayed().check(ViewAssertions.matches(ViewMatchers.withText(string(value))))

infix fun ViewInteraction.shouldBe(value: String): ViewInteraction =
        isDisplayed().check(ViewAssertions.matches(ViewMatchers.withText(value)))

infix fun ViewInteraction.shouldBe(value: Boolean): ViewInteraction =
        if (value) isDisplayed() else notDisplayed()

infix fun ViewInteraction.numberShouldBe(value: Int): ViewInteraction =
        check(RecyclerViewItemCountAssertion.withItemCount(value))

infix fun ViewInteraction.hasChildrenNumber(value: Int): ViewInteraction =
        check(ViewAssertions.matches(ViewMatchers.hasChildCount(value)))

infix fun ViewInteraction.hasBackground(value: Int): ViewInteraction =
        check(ViewAssertions.matches(withDrawable(value)))

infix fun ViewInteraction.hasToolbarTitle(title: CharSequence): ViewInteraction =
        isDisplayed().check(ViewAssertions.matches(withToolbarTitle(Matchers.`is`(title))))

infix fun ViewInteraction.hasToolbarTitle(@StringRes title: Int): ViewInteraction =
        isDisplayed().check(ViewAssertions.matches(withToolbarTitle(title)))

infix fun ViewInteraction.hasElevation(size: Float): ViewInteraction =
        check(ViewAssertions.matches(withElevation(size)))

infix fun ViewInteraction.hasElevation(size: Int): ViewInteraction =
        check(ViewAssertions.matches(withElevation(size)))

infix fun ViewInteraction.hasTextColor(@ColorRes color: Int): ViewInteraction =
        check(ViewAssertions.matches(ViewMatchers.hasTextColor(color)))

infix fun ViewInteraction.hasTextSize(dimen: Float): ViewInteraction =
        check(ViewAssertions.matches(withFontSize(dimen)))

infix fun ViewInteraction.hasText(text: String): ViewInteraction =
        isDisplayed().check(ViewAssertions.matches(ViewMatchers.withText(text)))

infix fun ViewInteraction.hasText(@StringRes textRes: Int): ViewInteraction =
        isDisplayed().check(ViewAssertions.matches(ViewMatchers.withText(textRes)))

infix fun ViewInteraction.recyclerItemCount(itemCount: Int): ViewInteraction =
        check(RecyclerViewItemCountAssertion.withItemCount(itemCount))

fun ViewInteraction.isClickable(): ViewInteraction = check(ViewAssertions.matches(ViewMatchers.isClickable()))
fun ViewInteraction.isNotClickable(): ViewInteraction = check(ViewAssertions.matches(Matchers.not(ViewMatchers.isClickable())))

fun ViewInteraction.isDisplayed(): ViewInteraction = check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
fun ViewInteraction.notDisplayed(): ViewInteraction = check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))

fun ViewInteraction.click(): ViewInteraction = perform(ViewActions.click())
fun ViewInteraction.scrollToPos(pos: Int): ViewInteraction =
        perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(pos))

fun ViewInteraction.scrollToViewWithId(id: Int): ViewInteraction = onViewById(id).perform(scrollTo())
fun ViewInteraction.scrollToViewWithTag(tag: String): ViewInteraction = onViewWithTag(tag).perform(scrollTo())
fun ViewInteraction.scrollToViewWithText(text: String): ViewInteraction = onViewWithText(text).perform(scrollTo())

fun ViewInteraction.scrollRecyclerToPos(pos: Int): ViewInteraction =
        perform(recyclerScrollTo(pos))

infix fun ViewInteraction.hasOrientation(orientation: Int): ViewInteraction =
        check(matches(withOrientation(orientation)))

infix fun ViewInteraction.hasGravity(gravity: Int): ViewInteraction =
        check(matches(withGravity(gravity)))
