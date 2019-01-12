package com.sserra.coffee

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build

fun loadDrawableFromResources(context: Context, resId: Int): Drawable? {
    try {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context.resources.getDrawable(resId)
        } else {
            context.getDrawable(resId)
        }
        //resourceName = context.resources.getResourceEntryName(resId)
    } catch (ignored: Resources.NotFoundException) {
        // view could be from a context unaware of the resource id.
    }
    return null
}

fun areDrawablesIdentical(drawableA: Drawable, drawableB: Drawable): Boolean {
    val stateA = drawableA.constantState
    val stateB = drawableB.constantState
    // If the constant state is identical, they are using the same drawable resource.
    // However, the opposite is not necessarily true.
    return stateA != null && stateB != null && stateA == stateB
            || getBitmap(drawableA).sameAs(getBitmap(drawableB))
}

fun getBitmap(drawable: Drawable): Bitmap {
    val result: Bitmap
    if (drawable is BitmapDrawable) {
        result = drawable.bitmap
    } else {
        var width = drawable.intrinsicWidth
        var height = drawable.intrinsicHeight
        // Some drawables have no intrinsic width - e.g. solid colours.
        if (width <= 0) {
            width = 1
        }
        if (height <= 0) {
            height = 1
        }

        result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
    }
    return result
}

enum class DrawableDirection {
    START, END, TOP, BOTTOM
}
