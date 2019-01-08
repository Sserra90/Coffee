package com.sserra.coffee

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.sserra.coffee.machers.childViewById
import org.hamcrest.Matchers.`is`

val rootView: ViewInteraction =
        Espresso.onView(ViewMatchers.isRoot())

fun onViewById(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))

fun onChildViewById(parentId: Int, id: Int): ViewInteraction =
        Espresso.onView(childViewById(ViewMatchers.withId(parentId), id))

fun onViewWithText(text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withText(text))

fun onViewWithTag(tag: String): ViewInteraction =
        Espresso.onView(withTagValue(`is`(tag)))

fun onViewWithDescription(res: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withContentDescription(res))

val <T> T.changeToPortrait: T
    get() {
        rootView.perform(OrientationChangeAction.orientationPortrait())
        return this
    }

val <T> T.changeToLandscape: T
    get() {
        rootView.perform(OrientationChangeAction.orientationLandscape())
        return this
    }
