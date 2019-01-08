package com.sserra.coffee.coffeepages

import android.content.Intent
import android.os.Bundle
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import com.sserra.coffee.changeToLandscape
import com.sserra.coffee.changeToPortrait
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.core.AllOf
import org.junit.Assert

@SafeVarargs
private fun checkIntended(aClass: Class<*>, extras: List<Matcher<Bundle>>) =
        Intents.intended(AllOf.allOf<Intent>(
                IntentMatchers.hasComponent(aClass.name),
                IntentMatchers.hasExtras(AllOf.allOf(extras))
        ))

private inline fun <reified T> isOpen(vararg extras: Pair<String, Any>) {
    checkIntended(T::class.java, extras.map { eqEntry(it.first, it.second) })
}

private fun <V> eqEntry(key: String, value: V): Matcher<Bundle> =
        BundleMatchers.hasEntry(Matchers.equalTo(key), Matchers.equalTo(value))

abstract class Page {

    fun changeToLandscape(): Page = apply { changeToLandscape }
    fun changeToPortrait(): Page = apply { changeToPortrait }
    fun navigateBack(): Page = apply { Espresso.pressBack() }
    fun navigateBackShouldWork(): Page = apply {
        // This is how Google does it
        // https://android.googlesource.com/platform/frameworks/testing/+/61a929bd4642b9042bfb05b85340c1761ab90733/espresso/espresso-lib-tests/src/androidTest/java/com/google/android/apps/common/testing/ui/espresso/action/KeyEventActionIntegrationTest.java
        try {
            navigateBack()
            Assert.fail("Should have thrown NoActivityResumedException")
        } catch (expected: NoActivityResumedException) {
        }
    }

    fun isOpen(aclass: Class<*>, vararg extras: Pair<String, Any>): Page = apply {
        checkIntended(aclass, extras.map { eqEntry(it.first, it.second) })
    }

    fun whenever(block: Page.() -> Unit = {}): Page = apply { block() }
}