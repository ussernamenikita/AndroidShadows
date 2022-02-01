package com.example.composeshadows.entity

import android.os.Parcelable
import androidx.core.graphics.ColorUtils
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

@Parcelize
data class Shadow(
    val dX: Float,
    val dY: Float,
    val radius: Float,
    val color: Int,
    val colorAlphaPercent: Float,
    val linearGradientParams: GradientParams
) : Parcelable {

    @IgnoredOnParcel
    val colorWithAlpha: Int by lazy {
        ColorUtils.setAlphaComponent(color, (255f * colorAlphaPercent).roundToInt())
    }

    companion object {
        val NO_SHADOW = Shadow(
            dX = 0f,
            dY = 0f,
            radius = 0f,
            color = 0,
            linearGradientParams = GradientParams.defaultLinearGradient(),
            colorAlphaPercent = 0f
        )
    }
}
