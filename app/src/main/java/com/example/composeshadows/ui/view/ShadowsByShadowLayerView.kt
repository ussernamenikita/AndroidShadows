package com.example.composeshadows.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.get
import androidx.core.view.updateLayoutParams
import com.example.composeshadows.entity.CustomShadowParams
import com.example.composeshadows.ui.state.CustomShadowParamsState
import com.example.composeshadows.ui.util.toPx

@Composable
fun ShadowsByShadowLayerView(
    width: Float,
    height: Float,
    state: MutableState<CustomShadowParamsState>,
    buttonCornerRadius: Float,
    buttonColor: Int
) {
    AndroidView(
        factory = { context ->
            val container = FrameLayout(context)
            val view = ShadowsByShadowLayerView(context)
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
            val view = it[0] as ShadowsByShadowLayerView
            view.roundRadius = buttonCornerRadius
            view.shadowParams = state.value.currentShadowParams
            view.buttonColor = buttonColor
            it.updateLayoutParams {
                this.width = ViewGroup.LayoutParams.MATCH_PARENT
                this.height = height.toInt() + 100.toPx.toInt()
            }
        })
}

class ShadowsByShadowLayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var shadowParams: CustomShadowParams = CustomShadowParams.noShadow()
        set(value) {
            field = value
            invalidate()
        }

    var roundRadius: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var buttonColor: Int = 0
        set(value) {
            field = value
            backgroundPaint.color = value
            invalidate()
        }

    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        shadowParams.layers.forEach {
            shadowPaint.setShadowLayer(it.radius, it.dX, it.dY, it.colorWithAlpha)
            canvas.drawRoundRect(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                roundRadius,
                roundRadius,
                shadowPaint
            )
        }
        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            roundRadius,
            roundRadius,
            backgroundPaint
        )
    }
}