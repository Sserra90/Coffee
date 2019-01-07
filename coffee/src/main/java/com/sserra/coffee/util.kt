package com.sserra.coffee

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.text.Html
import androidx.annotation.DimenRes
import androidx.test.platform.app.InstrumentationRegistry
import kotlin.math.roundToInt

@SuppressLint("StaticFieldLeak")
val context: Context = InstrumentationRegistry.getInstrumentation().context
@SuppressLint("StaticFieldLeak")
val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
val resources: Resources = targetContext.resources

val Int.toSp: Int
    get() = (this / Resources.getSystem().displayMetrics.scaledDensity).roundToInt()
val Float.toSp: Int
    get() = (this / Resources.getSystem().displayMetrics.scaledDensity).roundToInt()

val Float.toPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density)
val Float.toDp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()
val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).roundToInt()


fun html(resId: Int): String = Html.fromHtml(string(resId)).toString()
fun html(resId: Int, vararg objects: Any): String = Html.fromHtml(string(resId, *objects)).toString()

fun dimen(@DimenRes resId: Int): Float = targetContext.resources.getDimension(resId)

fun string(resId: Int): String = targetContext.getString(resId)
fun string(resId: Int, vararg objects: Any): String = targetContext.getString(resId, *objects)
