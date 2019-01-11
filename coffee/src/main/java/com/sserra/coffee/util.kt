package com.sserra.coffee

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Parcelable
import android.text.Html
import androidx.annotation.DimenRes
import androidx.test.platform.app.InstrumentationRegistry
import java.io.Serializable
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

inline fun <reified T> intentFor(block: (MutableMap<String, Any>).() -> Unit): Intent {
    val intent = Intent(targetContext, T::class.java)
    val bundle = Bundle()

    val extras = mutableMapOf<String, Any>()
    block.invoke(extras)

    if (extras.isNotEmpty()) {
        extras.iterator().forEach {
            when (it.value) {
                is String -> bundle.putString(it.key, it.value as String)
                is Int -> bundle.putInt(it.key, it.value as Int)
                is IntArray -> bundle.putIntArray(it.key, it.value as IntArray)
                is Double -> bundle.putDouble(it.key, it.value as Double)
                is DoubleArray -> bundle.putDoubleArray(it.key, it.value as DoubleArray)
                is Float -> bundle.putFloat(it.key, it.value as Float)
                is FloatArray -> bundle.putFloatArray(it.key, it.value as FloatArray)
                is Short -> bundle.putShort(it.key, it.value as Short)
                is ShortArray -> bundle.putShortArray(it.key, it.value as ShortArray)
                is Byte -> bundle.putByte(it.key, it.value as Byte)
                is ByteArray -> bundle.putByteArray(it.key, it.value as ByteArray)
                is Serializable -> bundle.putSerializable(it.key, it.value as Serializable)
                is Parcelable -> bundle.putParcelable(it.key, it.value as Parcelable)
            }
        }
    }
    intent.putExtras(bundle)
    return intent
}
