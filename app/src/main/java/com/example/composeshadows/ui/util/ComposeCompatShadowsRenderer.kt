package com.example.composeshadows.ui.util

import android.graphics.RectF
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.withTransform
import com.example.composeshadows.entity.GradientParams
import com.example.composeshadows.entity.Shadow

class ComposeCompatShadowsRenderer {

    companion object {
        private const val PERPENDICULAR_ANGLE = 90f
    }

    var outline: RectF = RectF(0f, 0f, 0f, 0f)

    var outlineCornerRadius: Float = 0f

    private var currentShadow: Shadow = Shadow.NO_SHADOW

    private var cornerGradientParams = createCornerParams()

    private lateinit var cornerPaint: Brush

    private lateinit var edgePaint: Brush

    private val shadowSize: Float
        get() = currentShadow.radius

    private val innerShadowSize: Float
        get() = shadowSize / 2

    private val outerArcRadius
        get() = outlineCornerRadius + shadowSize

    private val shadowColor
        get() = currentShadow.colorWithAlpha

    private val sideGradientParams
        get() = currentShadow.linearGradientParams

    fun paintCompatShadow(
        canvas: DrawScope
    ) {
        updateGradientValues()
        modifyCanvas(canvas, currentShadow.dX, currentShadow.dY) {

            // A rectangle with edges corresponding to the straight edges of the outline.
            val innerRect = RectF(outline)
            innerRect.inset(outlineCornerRadius, outlineCornerRadius)
            edgePaint = Brush.linearGradient(
                colorStops = sideGradientParams.colorsAndStops(shadowColor),
                tileMode = TileMode.Clamp,
                end = Offset(shadowSize + innerShadowSize, 0f)
            )
            // A rectangle used to represent the edge shadow.
            val edgeShadowRect = RectF()

            // left and right sides.
            edgeShadowRect.set(-shadowSize, 0f, innerShadowSize, innerRect.height())
            // Left shadow
            drawSideShadow(this, edgePaint, edgeShadowRect, innerShadowSize, innerRect.bottom, 2)
            // Right shadow
            drawSideShadow(
                this,
                edgePaint,
                edgeShadowRect,
                outline.right - innerShadowSize,
                outlineCornerRadius,
                0
            )
            // Draw shadow color between left and right shadows
            drawRect(
                Color(shadowColor),
                topLeft = Offset(innerShadowSize, outlineCornerRadius),
                size = Size(width = outline.width() - innerShadowSize * 2, innerRect.height())
            )

            // Top and bottom
            edgeShadowRect.set(-shadowSize, 0f, innerShadowSize, innerRect.width())
            // Top shadow
            drawSideShadow(this, edgePaint, edgeShadowRect, outlineCornerRadius, innerShadowSize, 3)
            // Bottom shadow
            drawSideShadow(
                this,
                edgePaint,
                edgeShadowRect,
                outline.width() - outlineCornerRadius,
                outline.bottom - innerShadowSize,
                1
            )

            // Draw shadow color between top shadow and inner rect
            drawRect(
                Color(shadowColor),
                topLeft = Offset(outlineCornerRadius, innerShadowSize),
                size = Size(width = innerRect.width(), outlineCornerRadius - innerShadowSize)
            )

            // Draw shadow color between bottom shadow and inner rect
            drawRect(
                Color(shadowColor),
                topLeft = Offset(outlineCornerRadius,outlineCornerRadius + innerRect.height()),
                size = Size(width = innerRect.width(), outlineCornerRadius - innerShadowSize)
            )

            // Draw corners.
            val path = androidx.compose.ui.graphics.Path()
            drawCornerShadow(
                this,
                cornerPaint,
                path,
                innerRect.right,
                innerRect.bottom,
                outerArcRadius,
                0
            )
            drawCornerShadow(this, cornerPaint, path, innerRect.left, innerRect.bottom, outerArcRadius, 1)
            drawCornerShadow(this, cornerPaint, path, innerRect.left, innerRect.top, outerArcRadius, 2)
            drawCornerShadow(this, cornerPaint, path, innerRect.right, innerRect.top, outerArcRadius, 3)
        }
    }

    fun setShadowParams(customShadowParams: Shadow) {
        this.currentShadow = customShadowParams
        updateGradientValues()
    }

    /**
     * Translate canvas by half of shadow size up, so that it appears that light is coming
     * slightly from above.
     */
    private fun modifyCanvas(
        canvas: DrawScope,
        shadowOffsetX: Float,
        shadowOffsetY: Float,
        action: DrawScope.() -> Unit
    ) {
        canvas.inset(shadowOffsetX, shadowOffsetY, -shadowOffsetX, -shadowOffsetY, action)
    }

    private fun drawSideShadow(
        canvas: DrawScope,
        edgePaint: Brush,
        edgeShadowRect: RectF,
        dx: Float,
        dy: Float,
        rotations: Int
    ) {
        if (isRectEmpty(edgeShadowRect)) {
            return
        }
        canvas.withTransform(transformBlock = {
            inset(dx, dy, -dx, -dy)
            rotate(rotations * PERPENDICULAR_ANGLE, pivot = Offset.Zero)
        }, drawBlock = {
            val edW = edgeShadowRect.width()
            val edH = edgeShadowRect.height()
            drawRect(
                brush = edgePaint,
                size = Size(edW, edH)
            )
        })
    }

    /**
     * @param canvas    Canvas to draw the rectangle on.
     * @param paint     Paint to use when drawing the corner.
     * @param path      A path to reuse. Prevents allocating memory for each path.
     * @param x         Center of circle, which this corner is a part of.
     * @param y         Center of circle, which this corner is a part of.
     * @param radius    radius of the arc
     * @param rotations number of quarter rotations before starting to paint the arc.
     */
    private fun drawCornerShadow(
        canvas: DrawScope,
        paint: Brush,
        path: androidx.compose.ui.graphics.Path,
        x: Float,
        y: Float,
        radius: Float,
        rotations: Int
    ) {
        canvas.inset(x, y, -x, -y) {
            path.reset()
            val rect = Rect(-radius, -radius, radius, radius)
            path.arcTo(
                rect,
                rotations * PERPENDICULAR_ANGLE,
                PERPENDICULAR_ANGLE,
                false
            )
            path.lineTo(0f, 0f)
            path.close()
            drawPath(path, paint)
        }
    }

    /**
     * Differs from [RectF.isEmpty] as this first converts the rect to int and then checks.
     *
     * This is required because BaseCanvas_Delegate#native_drawRect(long, float, float,
     * float,
     * float, long)} casts the coordinates to int and we want to ensure that it doesn't end up
     * drawing empty rectangles, which results in IllegalArgumentException.
     */
    private fun isRectEmpty(rect: RectF): Boolean {
        return rect.left.toInt() >= rect.right.toInt() || rect.top.toInt() >= rect.bottom.toInt()
    }

    private fun updateGradientValues() {
        this.cornerGradientParams = createCornerParams()
        cornerPaint = Brush.radialGradient(
            colorStops = cornerGradientParams.colorsAndStops(shadowColor),
            center = Offset.Zero,
            radius = outerArcRadius,
            tileMode = TileMode.Clamp
        )
    }

    private fun createCornerParams(): GradientParams {
        val innerShadowSize = innerShadowSize
        val points = sideGradientParams.getPoints().map { point ->
            val sizeValueByPoint = (shadowSize + innerShadowSize) * point
            val result =
                (sizeValueByPoint + outlineCornerRadius - innerShadowSize) / (outerArcRadius / 100f)
            result / 100
        }
        val newColorsAndPoints = sideGradientParams
            .colorsAndPoints
            .mapIndexed { index, gradientPointAndValue -> gradientPointAndValue.copy(point = points[index]) }
        return GradientParams(newColorsAndPoints)
    }
}

private fun GradientParams.colorsAndStops(shadowColor: Int): Array<Pair<Float, Color>> {
    val colors = getColors(shadowColor).map(::Color)
    return getPoints()
        .mapIndexed { index, point -> point to colors[index] }
        .toTypedArray()
}
