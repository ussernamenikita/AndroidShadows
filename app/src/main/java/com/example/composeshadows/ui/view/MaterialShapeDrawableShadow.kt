package com.example.composeshadows.ui.view

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.get
import androidx.core.view.updateLayoutParams
import com.example.composeshadows.ui.state.CustomShadowParamsState
import com.example.composeshadows.ui.util.toPx
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

@Composable
fun MaterialShapeDrawableShadow(
    width: Float,
    height: Float,
    state: MutableState<CustomShadowParamsState>,
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
            container
        }, update = {
            val shadowDrawable = MaterialShapeDrawable(
                ShapeAppearanceModel
                    .Builder()
                    .setAllCornerSizes(buttonCornerRadius)
                    .build()
            )

            val view = it[0]
            val layer = state.value.currentShadowParams.layers[0]
            with(shadowDrawable) {
                fillColor = ColorStateList.valueOf(buttonColor)
                setShadowColor(layer.colorWithAlpha)
                elevation = layer.radius
                shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
            }
            view.background = shadowDrawable
            it.updateLayoutParams {
                this.width = ViewGroup.LayoutParams.MATCH_PARENT
                this.height = height.toInt() + 100.toPx.toInt()
            }
        })
}