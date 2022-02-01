package com.example.composeshadows.ui.util

import android.content.res.Resources

val Int.toPx: Float
    get() = dpToPx(this).toFloat()

val Float.toPx: Float
    get() = dpToPx(this).toFloat()

val Float.toDp: Float
    get() = this / Resources.getSystem().displayMetrics.density

fun dpToPx(dp: Int): Int =
    (dp * Resources.getSystem().displayMetrics.density).toInt()

fun dpToPx(dp: Float): Int =
    (dp * Resources.getSystem().displayMetrics.density).toInt()
