package com.sserra.coffee

import android.content.Intent
import android.os.Bundle
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.core.AllOf

@SafeVarargs
fun checkIntended(aClass: Class<*>, extras: List<Matcher<Bundle>>) =
        Intents.intended(AllOf.allOf<Intent>(
                IntentMatchers.hasComponent(aClass.name),
                IntentMatchers.hasExtras(AllOf.allOf(extras))
        ))

inline fun <reified T> isOpen(vararg extras: Pair<String, Any>) {
    checkIntended(T::class.java, extras.map { eqEntry(it.first, it.second) })
}

fun <V> eqEntry(key: String, value: V): Matcher<Bundle> =
        BundleMatchers.hasEntry(Matchers.equalTo(key), Matchers.equalTo(value))
