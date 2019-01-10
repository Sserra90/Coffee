package com.sserra.coffee.coffeepages

import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import com.sserra.coffee.changeToLandscape
import com.sserra.coffee.changeToPortrait
import com.sserra.coffee.checkIntended
import com.sserra.coffee.eqEntry
import org.junit.Assert

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

    fun isOpen(aclass: Class<*>, vararg extras: Pair<String, Any>): Page<T> = apply {
        checkIntended(aclass, extras.map { eqEntry(it.first, it.second) })
    }

    fun whenever(block: T.() -> Unit = {}): T {
        @Suppress("UNCHECKED_CAST")
        block.invoke(this as T)
        return this
    }
}