package com.example.composeshadows.ui.view

import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.updateLayoutParams
import com.example.composeshadows.entity.CustomShadowParams
import com.example.composeshadows.ui.util.ShadowDrawable

@Composable
fun AndroidButtonWithGradientShadow(
    width: Float,
    height: Float,
    shadowParams: CustomShadowParams,
    buttonCornerRadius: Float,
    buttonColor: Int
) {
    AndroidView(
        factory = { context ->
            val container = FrameLayout(context)
            val view = View(context)
            val layoutParams = FrameLayout.LayoutParams(
                width.toInt(),
                height.toInt(),
                Gravity.CENTER
            )
            container.addView(view, layoutParams)
            container.clipChildren = false
            container.clipToPadding = false

            val shadowDrawable = ShadowDrawable(buttonCornerRadius)
            shadowDrawable.shadowParams = shadowParams
            val roundRect =
                ShapeDrawable(
                    RoundRectShape(
                        FloatArray(8) { buttonCornerRadius },
                        null,
                        null
                    )
                )
            roundRect.paint.color = buttonColor
            val background = LayerDrawable(arrayOf(shadowDrawable, roundRect))
            view.background = background
            container
        }, update = {
            it.updateLayoutParams {
                this.width = ViewGroup.LayoutParams.MATCH_PARENT
                this.height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        })
}