package com.example.composeshadows.ui.view

import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.get
import androidx.core.view.updateLayoutParams
import com.example.composeshadows.ui.state.CustomShadowParamsState
import com.example.composeshadows.ui.util.toPx

@Composable
fun AndroidViewWithSDKShadow(
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

            val roundRect =
                ShapeDrawable(
                    RoundRectShape(
                        FloatArray(8) { buttonCornerRadius },
                        null,
                        null
                    )
                )
            roundRect.paint.color = buttonColor
            view.background = roundRect
            container
        }, update = {
            val view = it[0]
            val layer = state.value.currentShadowParams.layers[0]
            view.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(
                        layer.dX.toInt(),
                        layer.dY.toInt(),
                        layer.dX.toInt() + view.width,
                        layer.dY.toInt() + view.height,
                        buttonCornerRadius
                    )
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                view.outlineAmbientShadowColor = Color.BLACK
                view.outlineSpotShadowColor = Color.BLACK
            }
            view.elevation = layer.radius
            it.updateLayoutParams {
                this.width = ViewGroup.LayoutParams.MATCH_PARENT
                this.height = height.toInt() + 100.toPx.toInt()
            }
        })
}