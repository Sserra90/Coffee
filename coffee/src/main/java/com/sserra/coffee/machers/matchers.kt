package com.sserra.coffee.machers

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.method.TransformationMethod
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.internal.util.Checks
import com.sserra.coffee.*
import junit.framework.Assert.fail
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import java.lang.reflect.Field
import kotlin.math.roundToInt

/**
 * Finds the n child of a parent view.
 */
fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("position $childPosition of parent ")
            parentMatcher.describeTo(description)
        }

        override fun matchesSafely(view: View): Boolean {
            if (view.parent !is ViewGroup) return false
            val parent = view.parent as ViewGroup
            return (parentMatcher.matches(parent)
                    && parent.childCount > childPosition
                    && parent.getChildAt(childPosition) == view)
        }
    }
}

fun childViewById(parentMatcher: Matcher<View>, childId: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("id $childId of parent ")
            parentMatcher.describeTo(description)
        }

        override fun matchesSafely(view: View): Boolean {
            if (view.parent !is ViewGroup) return false
            val parent = view.parent as ViewGroup
            return parentMatcher.matches(parent) && view.id == childId
        }
    }
}

/**
 * Custom matcher to check the number of children in viewgroups
 *
 * @param numChildrenMatcher number of children
 */
fun hasNrChildren(numChildrenMatcher: Matcher<Int>): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        public override fun matchesSafely(view: View): Boolean =
                view is ViewGroup && numChildrenMatcher.matches(view.childCount)

        override fun describeTo(description: Description) {
            description.appendText(" a view with # children is ")
            numChildrenMatcher.describeTo(description)
        }
    }
}

/**
 * Custom matcher to check the background color of a view
 *
 * @param colorRes color resource
 */
fun hasBackgroundColor(colorRes: Int): Matcher<View> {
    Checks.checkNotNull(colorRes)

    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("background color: $colorRes")
        }

        override fun matchesSafely(item: View?): Boolean {

            val context = item?.context
            val textViewColor = (item?.background as ColorDrawable).color
            val expectedColor: Int?

            expectedColor = if (Build.VERSION.SDK_INT <= 22) {
                context?.resources?.getColor(colorRes)
            } else {
                context?.getColor(colorRes)
            }

            return textViewColor == expectedColor
        }
    }
}

/**
 * Custom matcher to check text color for TextView
 *
 * @param color color
 */
fun withTextColor(color: Int): Matcher<View> {
    Checks.checkNotNull(color)
    return object : BoundedMatcher<View, TextView>(TextView::class.java) {
        public override fun matchesSafely(warning: TextView): Boolean = color == warning.currentTextColor
        override fun describeTo(description: Description) {
            description.appendText("with text color: ")
        }
    }
}

/**
 * Custom matcher to check text color for TextView
 *
 * @param resourceId drawable resource to compare
 */
fun withDrawable(resourceId: Int): TypeSafeMatcher<View> {
    Checks.checkNotNull(resourceId)

    return object : TypeSafeMatcher<View>() {

        private var resourceName: String? = null
        private var expectedDrawable: Drawable? = null

        override fun matchesSafely(target: View): Boolean {
            if (expectedDrawable == null) {
                loadDrawableFromResources(target.context, resourceId)
            }

            if (invalidExpectedDrawable()) {
                return false
            }

            if (target is ImageView) {
                return hasImage(target) || hasBackground(target)
            }

            return if (target is TextView) {
                hasCompoundDrawable(target) || hasBackground(target)
            } else hasBackground(target)
        }

        private fun invalidExpectedDrawable(): Boolean = expectedDrawable == null

        private fun hasImage(target: ImageView): Boolean = isSameDrawable(target.drawable)

        private fun hasCompoundDrawable(target: TextView): Boolean {
            for (drawable in target.compoundDrawables) {
                if (isSameDrawable(drawable)) {
                    return true
                }
            }
            return false
        }

        private fun hasBackground(target: View): Boolean = isSameDrawable(target.background)

        private fun isSameDrawable(drawable: Drawable?): Boolean {
            return if (drawable == null) {
                false
            } else areDrawablesIdentical(expectedDrawable!!, drawable)
        }

        override fun describeTo(description: Description) {
            description.appendText("with drawable from resource id: ")
            description.appendValue(resourceId)
            if (resourceName != null) {
                description.appendText("[")
                description.appendText(resourceName)
                description.appendText("]")
            }
        }
    }
}

/**
 * Custom matcher to check [TextView] drawables
 *
 * @param resId drawable resource to compare
 * @param direction drawable direction
 */
fun withTextDrawable(resId: Int, direction: DrawableDirection): Matcher<View> {

    return object : TypeSafeMatcher<View>() {

        private var resourceName: String? = null
        private var expectedDrawable: Drawable? = null

        override fun matchesSafely(item: View): Boolean {
            if (item !is TextView) {
                return false
            }

            val d: Drawable = when (direction) {
                DrawableDirection.BOTTOM -> item.compoundDrawables[3]
                DrawableDirection.TOP -> item.compoundDrawables[1]
                DrawableDirection.START -> item.compoundDrawables[0]
                DrawableDirection.END -> item.compoundDrawables[2]
            } ?: return false

            expectedDrawable = loadDrawableFromResources(item.context, resId)
            if (expectedDrawable == null) {
                return false
            }

            return areDrawablesIdentical(expectedDrawable!!, d)
        }

        override fun describeTo(description: Description) {
            description.appendText("with drawable from resource id: ")
            description.appendValue(resId)
            if (resourceName != null) {
                description.appendText("[")
                description.appendText(resourceName)
                description.appendText("]")
            }
        }
    }
}

/**
 * Custom matcher to check [Toolbar] title
 *
 * @param titleRes title resource
 */
fun withToolbarTitle(titleRes: Int): Matcher<View> {
    return object : BoundedMatcher<View, Toolbar>(Toolbar::class.java) {

        var view: Toolbar? = null

        public override fun matchesSafely(toolbar: Toolbar): Boolean {
            view = toolbar
            val expectedTitle = toolbar.context.resources?.getString(titleRes)
            val textMatcher = Matchers.`is`(expectedTitle)
            return textMatcher!!.matches(toolbar.title)
        }

        override fun describeTo(description: Description) {
            description.appendText("with toolbar title: ${view?.title}")
        }
    }
}

fun withToolbarTitle(textMatcher: Matcher<CharSequence>): Matcher<View> {
    return object : BoundedMatcher<View, Toolbar>(Toolbar::class.java) {
        public override fun matchesSafely(toolbar: Toolbar): Boolean = textMatcher.matches(toolbar.title)

        override fun describeTo(description: Description) {
            description.appendText("with toolbar title: ")
            textMatcher.describeTo(description)
        }
    }
}

/**
 * Custom matcher to check [TextView] font size
 *
 * @param expectedSize expected font size
 */
fun withFontSize(expectedSize: Float): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {

        override fun matchesSafely(target: View): Boolean {
            return if (target !is TextView) {
                false
            } else {
                return target.textSize.toSp.compareTo(expectedSize) == 0
            }
        }

        override fun describeTo(description: Description) {
            description.appendText("with fontSize: ")
            description.appendValue(expectedSize)
        }
    }
}

/**
 * Custom matcher to check [View] elevation
 *
 * @param expectedSize expected elevation size
 */
fun withElevation(expectedSize: Float): Matcher<View> = withElevation(expectedSize.toInt())

fun withElevation(expectedSize: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {

        private var view: View? = null

        override fun matchesSafely(target: View): Boolean {
            view = target
            return target.elevation.toDp.roundToInt() == expectedSize
        }

        override fun describeTo(description: Description) {
            description.appendText("with elevation size: ${view?.elevation}")
            description.appendValue(expectedSize)
        }
    }
}

enum class LayoutManager {
    LINEAR, GRID
}

/**
 * Custom matcher to check [RecyclerView] layout manager
 *
 * @param type expected layout manager type
 */
fun withLayoutManager(type: LayoutManager): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {

        private var view: RecyclerView? = null

        override fun matchesSafely(target: View): Boolean {

            if (target !is RecyclerView) {
                return false
            }

            view = target
            return when (target.layoutManager) {
                is LinearLayoutManager -> type == LayoutManager.LINEAR
                is StaggeredGridLayoutManager -> type == LayoutManager.GRID
                else -> false
            }
        }

        override fun describeTo(description: Description) {
            description.appendText("with layout manager: ${view?.layoutManager.toString()}")
            description.appendValue(" expected $type")
        }
    }
}

/**
 * Custom matcher to check [LinearLayout] orientation
 *
 * @param orientation expected orientation
 */
fun withOrientation(orientation: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {

        private var actualOrientation: Int? = null

        override fun matchesSafely(item: View): Boolean {
            if (item !is LinearLayout) {
                return false
            }

            actualOrientation = item.orientation
            return actualOrientation == orientation
        }

        override fun describeTo(description: Description) {
            description.appendText("with orientation: $actualOrientation")
            description.appendValue(" expected $orientation")
        }

    }
}

/**
 * Custom matcher to check views gravity
 *
 * @param gravity expected gravity
 */
fun withGravity(gravity: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {

        private var actualGravity: Int? = null

        override fun matchesSafely(target: View): Boolean {
            actualGravity = when (target) {
                is TextView -> target.gravity
                is LinearLayout -> getGravity(target)
                is RelativeLayout -> target.gravity
                else -> return false
            }
            return gravity == actualGravity
        }

        override fun describeTo(description: Description) {
            description.appendText("with gravity: $actualGravity")
            description.appendValue(" expected $gravity")
        }

        private fun getGravity(linearLayout: LinearLayout): Int {
            val field: Field = LinearLayout::class.java.getDeclaredField("mGravity")
            field.isAccessible = true
            return field.getInt(linearLayout)
        }
    }
}

/**
 * Custom matcher to check views gravity
 *
 * @param gravity expected gravity
 */
/*fun withLayoutGravity(layoutGravity: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {

        private var actualGravity: Int? = null

        override fun matchesSafely(target: View): Boolean {
            actualGravity = when (target) {
                is LinearLayout -> getGravity(target)
                is RelativeLayout -> (target.layoutParams as RelativeLayout.LayoutParams).
                else -> return false
            }
            return layoutGravity == actualGravity
        }

        override fun describeTo(description: Description) {
            description.appendText("with gravity: $actualGravity")
            description.appendValue(" expected $gravity")
        }

        private fun getGravity(linearLayout: LinearLayout): Int {
            val field: Field = LinearLayout::class.java.getDeclaredField("mGravity")
            field.isAccessible = true
            return field.getInt(linearLayout)
        }
    }
}*/

/**
 * Custom matcher to check [TextView] textAllCaps
 *
 * @param allCaps
 */
fun withTextAllCaps(allCaps: Boolean): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {

        private var view: TextView? = null

        override fun matchesSafely(target: View): Boolean {
            if (target !is TextView) {
                return false
            }
            view = target
            return isAllCaps(target) == allCaps
        }

        private fun isAllCaps(view: TextView): Boolean {
            val method: TransformationMethod? = view.transformationMethod
            return method != null && method.getTransformation("text", view).toString() == "TEXT"
        }

        override fun describeTo(description: Description) {
            description.appendText("with textAllCaps: ${view?.transformationMethod}")
            description.appendValue(" expected $allCaps")
        }
    }
}

/**
 * Custom matcher to check [TextView] textStyle
 *
 * @param textStyle
 */
fun withTextStyle(textStyle: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {

        private var view: TextView? = null

        override fun matchesSafely(target: View): Boolean {
            if (target !is TextView) {
                return false
            }
            view = target
            return target.typeface.style == textStyle
        }

        override fun describeTo(description: Description) {
            description.appendText("with textStyle: ${view?.typeface}")
            description.appendValue(" expected $textStyle")
        }
    }
}

/**
 * Custom matcher to check [ImageView] scaleType
 *
 * @param scaleType
 */
fun withScaleType(scaleType: ImageView.ScaleType): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {

        private var view: ImageView? = null

        override fun matchesSafely(target: View): Boolean {
            if (target !is ImageView) {
                return false
            }
            view = target
            return target.scaleType == scaleType
        }

        override fun describeTo(description: Description) {
            description.appendText("with textAllCaps: ${view?.scaleType}")
            description.appendValue(" expected $scaleType")
        }
    }
}

/**
 * Custom matcher to check [View] weight property
 *
 * @param weight
 */
fun withWeight(weight: Float): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {

        private var actualWeight: Int? = null

        override fun matchesSafely(target: View): Boolean {
            if (target.layoutParams !is LinearLayout.LayoutParams) {
                fail("View $target parent is not a LinearLayout")
            }

            val actualWeight = (target.layoutParams as LinearLayout.LayoutParams).weight
            return weight == actualWeight
        }

        override fun describeTo(description: Description) {
            description.appendText("with weight: $actualWeight")
            description.appendValue(" expected $weight")
        }
    }
}
