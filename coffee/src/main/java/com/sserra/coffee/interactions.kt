package com.sserra.coffee

import android.widget.ImageView
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

infix fun ViewInteraction.shouldBe(value: Boolean): ViewInteraction =
        if (value) isDisplayed() else notDisplayed()

infix fun ViewInteraction.hasChildrenNumber(value: Int): ViewInteraction =
        check(ViewAssertions.matches(ViewMatchers.hasChildCount(value)))

infix fun ViewInteraction.hasBackground(value: Int): ViewInteraction =
        check(ViewAssertions.matches(withDrawable(value)))

infix fun ViewInteraction.hasToolbarTitle(title: CharSequence): ViewInteraction =
        isDisplayed().check(ViewAssertions.matches(withToolbarTitle(Matchers.`is`(title))))

infix fun ViewInteraction.hasToolbarTitle(@StringRes title: Int): ViewInteraction =
        isDisplayed().check(ViewAssertions.matches(withToolbarTitle(title)))

infix fun ViewInteraction.hasToolbarSubTitle(title: CharSequence): ViewInteraction =
        isDisplayed().check(ViewAssertions.matches(withToolbarSubTitle(Matchers.`is`(title))))

infix fun ViewInteraction.hasToolbarSubTitle(@StringRes title: Int): ViewInteraction =
        isDisplayed().check(ViewAssertions.matches(withToolbarSubTitle(title)))

infix fun ViewInteraction.hasElevation(size: Float): ViewInteraction =
        check(ViewAssertions.matches(withElevation(size)))

infix fun ViewInteraction.hasWeight(weight: Float): ViewInteraction =
        check(ViewAssertions.matches(withWeight(weight)))

infix fun ViewInteraction.hasAlpha(alpha: Float): ViewInteraction =
        check(ViewAssertions.matches(ViewMatchers.withAlpha(alpha)))

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

infix fun ViewInteraction.hasTextAllCaps(allCaps: Boolean): ViewInteraction =
        check(matches(withTextAllCaps(allCaps)))

infix fun ViewInteraction.hasScaleType(scaleType: ImageView.ScaleType): ViewInteraction =
        check(matches(withScaleType(scaleType)))

infix fun ViewInteraction.hasTypeface(typeface: Int): ViewInteraction =
        check(matches(withTextStyle(typeface)))

fun ViewInteraction.hasTextDrawable(resId: Int, direction: DrawableDirection): ViewInteraction =
        check(matches(withTextDrawable(resId, direction)))
