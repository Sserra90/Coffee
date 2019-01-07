package com.sserra.coffee

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import com.sserra.coffee.machers.childViewById

val rootView: ViewInteraction =
        Espresso.onView(ViewMatchers.isRoot())

fun onViewById(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))

fun onChildViewById(parentId: Int, id: Int): ViewInteraction =
        Espresso.onView(childViewById(ViewMatchers.withId(parentId), id))

fun onViewWithText(text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withText(text))

fun onViewWithDescription(res: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withContentDescription(res))
