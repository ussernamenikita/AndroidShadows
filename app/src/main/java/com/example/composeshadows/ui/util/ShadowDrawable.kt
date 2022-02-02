package com.example.composeshadows.ui.util

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat.OPAQUE
import android.graphics.RectF
import android.graphics.drawable.Drawable
import com.example.composeshadows.entity.CustomShadowParams

class ShadowDrawable(
    private val cornerRadius: Float
) : Drawable() {

    private val shadowRenderer: CompatShadowsRenderer = CompatShadowsRenderer()

    var shadowParams: CustomShadowParams = CustomShadowParams.noShadow()

    override fun draw(canvas: Canvas) {
        val w = bounds.width().toFloat()
        val h = bounds.height().toFloat()
        for (layer in shadowParams.layers) {
            shadowRenderer.paintCompatShadow(
                canvas = canvas,
                outline = RectF(0f, 0f, w, h),
                outlineCornerRadius = cornerRadius,
                shadow = layer
            )
        }
    }

    override fun setAlpha(alpha: Int) = Unit

    override fun setColorFilter(colorFilter: ColorFilter?) = Unit

    override fun getOpacity(): Int = OPAQUE
}