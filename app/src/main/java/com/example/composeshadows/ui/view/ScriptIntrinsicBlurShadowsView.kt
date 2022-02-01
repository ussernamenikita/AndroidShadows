package com.example.composeshadows.ui.view

import android.content.Context
import android.graphics.Bitmap
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
import androidx.core.graphics.scaleMatrix
import androidx.core.graphics.times
import androidx.core.graphics.translationMatrix
import androidx.core.graphics.withMatrix
import androidx.core.view.get
import androidx.core.view.updateLayoutParams
import com.example.composeshadows.entity.CustomShadowParams
import com.example.composeshadows.ui.state.CustomShadowParamsState
import com.example.composeshadows.ui.util.RenderScriptBlur
import com.example.composeshadows.ui.util.toPx
import kotlin.math.roundToInt

@Composable
fun ScriptIntrinsicBlurShadowsView(
    width: Float,
    height: Float,
    state: MutableState<CustomShadowParamsState>,
    buttonCornerRadius: Float,
    buttonColor: Int
) {
    AndroidView(
        factory = { context ->
            val container = FrameLayout(context)
            val view = ScriptIntrinsicBlurShadowsView(context)
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
            val view = it[0] as ScriptIntrinsicBlurShadowsView
            view.roundRadius = buttonCornerRadius
            view.shadowParams = state.value.currentShadowParams
            view.buttonColor = buttonColor
            it.updateLayoutParams {
                this.width = ViewGroup.LayoutParams.MATCH_PARENT
                this.height = height.toInt() + 100.toPx.toInt()
            }
        })
}

class ScriptIntrinsicBlurShadowsView @JvmOverloads constructor(
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
    private val blurScript = RenderScriptBlur(context)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawShadow(canvas)
    }

    private fun drawShadow(canvas: Canvas) {
        val shadow = shadowParams.layers[0]
        if (shadow.radius <= 0) return

        val width: Float = width.toFloat() + shadow.radius * 2f + shadow.dX
        val height: Float = height.toFloat() + shadow.radius * 2f + shadow.dY

        val maxBlurRadius = 25f
        val scale = (maxBlurRadius / shadow.radius).coerceAtMost(1f)
        val shadowScaleMatrix = scaleMatrix(scale, scale)
        val shadowTranslationMatrix = translationMatrix(shadow.radius, shadow.radius)

        val drawableScaleMatrix = scaleMatrix(1f / scale, 1f / scale)
        val drawableTranslateMatrix = translationMatrix(-shadow.radius, -shadow.radius)

        val shadowLayer = Bitmap.createBitmap(
            ((width*2) * scale).roundToInt(),
            ((height*2) * scale).roundToInt(),
            Bitmap.Config.ARGB_8888
        )
        shadowPaint.color = shadow.colorWithAlpha
        val shadowCanvas = Canvas(shadowLayer)
        shadowCanvas.withMatrix(shadowScaleMatrix * shadowTranslationMatrix) {
            drawRoundRect(
                0f,
                0f,
                width,
                height,
                roundRadius,
                roundRadius,
                shadowPaint
            )
        }
        blurScript.blur(shadowLayer, (shadow.radius * scale).coerceIn(0f, maxBlurRadius))

        canvas.withMatrix(drawableScaleMatrix*drawableTranslateMatrix) {
            val offsetX = shadow.dX * scale - shadow.radius
            val offsetY = shadow.dY * scale - shadow.radius
            canvas.drawBitmap(shadowLayer, offsetX, offsetY, null)
        }
        shadowLayer.recycle()
        canvas.drawRoundRect(
            0f,
            0f,
            getWidth().toFloat(),
            getHeight().toFloat(),
            roundRadius,
            roundRadius,
            backgroundPaint
        )
    }

}