package com.sserra.coffee

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher

/**
 * An Espresso ViewAction that changes the orientation of the screen. Use like this:
 * `onView(isRoot()).perform(orientationPortrait());` or this: `onView(isRoot()).perform(orientationLandscape());`
 */
class OrientationChangeAction private constructor(private val orientation: Int) : ViewAction {

    override fun getConstraints(): Matcher<View> = isRoot()

    override fun getDescription(): String = "change orientation to $orientation"

    override fun perform(uiController: UiController, view: View) {
        uiController.loopMainThreadUntilIdle()

        var activity = getActivity(view.context)
        if (activity == null && view is ViewGroup) {
            val c = view.childCount
            var i = 0
            while (i < c && activity == null) {
                activity = getActivity(view.getChildAt(i).context)
                ++i
            }
        }
        activity?.requestedOrientation = orientation
    }

    private fun getActivity(ctx: Context): Activity? {
        var context = ctx
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    companion object {
        fun orientationLandscape(): ViewAction = OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        fun orientationPortrait(): ViewAction = OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }
}

/**
 * State to scroll the recycler view, the ViewActions scroll state, doesn't not trigger
 * the scroll listener in RecyclerView, i guess because of a bug in the RecyclerView, calling
 * the smoothScroll method on the layoutManager solves the problem.
 *
 * @param position to scroll
 */
fun recyclerScrollTo(position: Int): ViewAction {
    return object : ViewAction {

        override fun getConstraints(): Matcher<View> = org.hamcrest.Matchers.allOf(
                ViewMatchers.isAssignableFrom(RecyclerView::class.java),
                ViewMatchers.isDisplayed()
        )

        override fun getDescription(): String = "scroll RecyclerView to position: $position"

        override fun perform(uiController: UiController, view: View) {
            val recyclerView = view as RecyclerView
            recyclerView.layoutManager!!.smoothScrollToPosition(
                    recyclerView, null, position
            )
            uiController.loopMainThreadForAtLeast(2000)
        }
    }
}