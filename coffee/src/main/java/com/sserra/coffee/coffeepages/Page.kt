package com.sserra.coffee.coffeepages

import android.app.Activity
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.contrib.ActivityResultMatchers
import androidx.test.espresso.contrib.ActivityResultMatchers.hasResultCode
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.rule.ActivityTestRule
import com.sserra.coffee.changeToLandscape
import com.sserra.coffee.changeToPortrait
import com.sserra.coffee.checkIntended
import com.sserra.coffee.eqEntry
import org.junit.Assert
import java.lang.Thread.sleep

open class Page<out T : Page<T>> {

    operator fun invoke(function: T.() -> Unit) {
        @Suppress("UNCHECKED_CAST")
        function.invoke(this as T)
    }

    fun changeToLandscape(): Page<T> = apply { changeToLandscape }
    fun changeToPortrait(): Page<T> = apply { changeToPortrait }
    fun navigateBack(): Page<T> = apply { Espresso.pressBack() }
    fun navigateBackShouldWork(): Page<T> = apply {
        // This is how Google does it
        // https://android.googlesource.com/platform/frameworks/testing/+/61a929bd4642b9042bfb05b85340c1761ab90733/espresso/espresso-lib-tests/src/androidTest/java/com/google/android/apps/common/testing/ui/espresso/action/KeyEventActionIntegrationTest.java
        try {
            navigateBack()
            Assert.fail("Should have thrown NoActivityResumedException")
        } catch (expected: NoActivityResumedException) {
        }
    }

    inline fun <reified V> isOpen(vararg extras: Pair<String, Any>): Page<T> = apply {
        checkIntended(V::class.java, extras.map { eqEntry(it.first, it.second) })
    }

    infix fun isClosed(rule: ActivityTestRule<*>) {
        sleep(2000) // We need this
        assertThat(rule.activityResult, hasResultCode(Activity.RESULT_CANCELED))
    }

    fun whenever(block: T.() -> Unit = {}): T {
        @Suppress("UNCHECKED_CAST")
        block.invoke(this as T)
        return this
    }
}