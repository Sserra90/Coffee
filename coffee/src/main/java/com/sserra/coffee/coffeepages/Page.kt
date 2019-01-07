package com.sserra.coffee.coffeepages

import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import com.sserra.coffee.*
import org.junit.Assert

abstract class Page {
    fun changeToLandscape(): Page = apply { changeToLandscape }
    fun changeToPortrait(): Page = apply { changeToPortrait }
    fun navigateBack(): Page = apply { Espresso.pressBack() }
    fun navigateBackShouldWork() : Page = apply {
        // This is how Google does it
        // https://android.googlesource.com/platform/frameworks/testing/+/61a929bd4642b9042bfb05b85340c1761ab90733/espresso/espresso-lib-tests/src/androidTest/java/com/google/android/apps/common/testing/ui/espresso/action/KeyEventActionIntegrationTest.java
        try {
            navigateBack()
            Assert.fail("Should have thrown NoActivityResumedException")
        } catch (expected: NoActivityResumedException) {
        }
    }
    fun whenever(block: Page.() -> Unit = {}): Page = apply { block() }
}