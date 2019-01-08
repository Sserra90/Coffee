package com.sserra.coffee

import android.app.Activity
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.rule.ActivityTestRule

inline fun <reified T : Activity> activityRule(touchMode: Boolean = true, launch: Boolean = false) =
        ActivityTestRule(T::class.java, touchMode, launch)

inline fun <reified T : Activity> intentRule(touchMode: Boolean = true, launch: Boolean = false) =
        IntentsTestRule(T::class.java, touchMode, launch)
