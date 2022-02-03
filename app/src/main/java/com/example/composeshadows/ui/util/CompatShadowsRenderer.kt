package com.example.composeshadows.ui.util

import android.graphics.*
import com.example.composeshadows.entity.GradientParams
import com.example.composeshadows.entity.Shadow

class CompatShadowsRenderer {

    companion object {
        private const val PERPENDICULAR_ANGLE = 90f
    }

    private var currentShadow: Shadow = Shadow.NO_SHADOW

    private var cornerGradientParams = createCornerParams()

    private var outline: RectF = RectF(0f, 0f, 0f, 0f)

    private var outlineCornerRadius: Float = 0f

    private val cornerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val edgePaint = Paint(cornerPaint).apply {
        isAntiAlias = true
    }

    private val contentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

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
        canvas: Canvas,
        outline: RectF,
        outlineCornerRadius: Float,
        shadow: Shadow
    ) {
        this.currentShadow = shadow
        this.outline = outline
        this.outlineCornerRadius = outlineCornerRadius

        contentPaint.color = shadow.colorWithAlpha
        updateGradientValues()

        val saved = modifyCanvas(canvas, currentShadow.dX, currentShadow.dY)
        val path = Path()
        // A rectangle with edges corresponding to the straight edges of the outline.
        val innerRect = RectF(outline)
        innerRect.inset(outlineCornerRadius, outlineCornerRadius)

        // A rectangle used to represent the edge shadow.
        val edgeShadowRect = RectF()

        // left and right sides.
        edgeShadowRect.set(-shadowSize, 0f, innerShadowSize, innerRect.height())
        // Left shadow
        drawSideShadow(canvas, edgePaint, edgeShadowRect, outline.left, innerRect.top, 0)
        // Right shadow
        drawSideShadow(canvas, edgePaint, edgeShadowRect, outline.right, innerRect.bottom, 2)
        // Draw shadow color between left and right shadows
        canvas.drawRect(
            innerShadowSize,
            outlineCornerRadius,
            outline.right - innerShadowSize,
            innerRect.bottom,
            contentPaint
        )

        // Top and bottom
        edgeShadowRect.set(-shadowSize, 0f, innerShadowSize, innerRect.width())
        // Top shadow
        drawSideShadow(canvas, edgePaint, edgeShadowRect, innerRect.right, outline.top, 1)
        // Bottom shadow
        drawSideShadow(canvas, edgePaint, edgeShadowRect, outlineCornerRadius, outline.bottom, 3)

        // Draw shadow color between top shadow and inner rect
        canvas.drawRect(
            outlineCornerRadius,
            innerShadowSize,
            outline.right - outlineCornerRadius,
            innerShadowSize + outlineCornerRadius - innerShadowSize,
            contentPaint
        )

        // Draw shadow color between bottom shadow and inner rect
        canvas.drawRect(
            outlineCornerRadius,
            outlineCornerRadius + innerRect.height(),
            outline.right - outlineCornerRadius,
            outline.bottom - innerShadowSize,
            contentPaint
        )

        // Draw corners.
        drawCornerShadow(
            canvas,
            cornerPaint,
            path,
            innerRect.right,
            innerRect.bottom,
            outerArcRadius,
            0
        )
        drawCornerShadow(
            canvas,
            cornerPaint,
            path,
            innerRect.left,
            innerRect.bottom,
            outerArcRadius,
            1
        )
        drawCornerShadow(
            canvas,
            cornerPaint,
            path,
            innerRect.left,
            innerRect.top,
            outerArcRadius,
            2
        )
        drawCornerShadow(
            canvas,
            cornerPaint,
            path,
            innerRect.right,
            innerRect.top,
            outerArcRadius,
            3
        )
        canvas.restoreToCount(saved)
    }

    /**
     * Translate canvas by half of shadow size up, so that it appears that light is coming
     * slightly from above.
     */
    private fun modifyCanvas(
        canvas: Canvas,
        shadowOffsetX: Float,
        shadowOffsetY: Float
    ): Int {
        val saved = canvas.save()
        val transformMatrix = Matrix()
        // Двигаем ниже и правее
        transformMatrix.setTranslate(
            shadowOffsetX,
            shadowOffsetY
        )
        canvas.concat(transformMatrix)
        return saved
    }

    private fun drawSideShadow(
        canvas: Canvas,
        edgePaint: Paint,
        edgeShadowRect: RectF,
        dx: Float,
        dy: Float,
        rotations: Int
    ) {
        if (isRectEmpty(edgeShadowRect)) {
            return
        }
        val saved = canvas.save()
        canvas.translate(dx, dy)
        canvas.rotate(rotations * PERPENDICULAR_ANGLE)
        canvas.drawRect(edgeShadowRect, edgePaint)
        canvas.restoreToCount(saved)
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
        canvas: Canvas,
        paint: Paint,
        path: Path,
        x: Float,
        y: Float,
        radius: Float,
        rotations: Int
    ) {
        val saved = canvas.save()
        canvas.translate(x, y)
        path.reset()
        path.arcTo(
            -radius,
            -radius,
            radius,
            radius,
            rotations * PERPENDICULAR_ANGLE,
            PERPENDICULAR_ANGLE,
            false
        )
        path.lineTo(0f, 0f)
        path.close()
        canvas.drawPath(path, paint)
        canvas.restoreToCount(saved)
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
        cornerPaint.shader = RadialGradient(
            0f,
            0f,
            outerArcRadius,
            cornerGradientParams.getColors(shadowColor),
            cornerGradientParams.getPoints(),
            Shader.TileMode.CLAMP
        )
        edgePaint.shader = LinearGradient(
            innerShadowSize,
            0f,
            -shadowSize,
            0f,
            sideGradientParams.getColors(shadowColor),
            sideGradientParams.getPoints(),
            Shader.TileMode.CLAMP
        )
    }

    private fun createCornerParams(): GradientParams {
        val innerShadowSize = innerShadowSize
        val points = sideGradientParams.getPoints().map { point ->
            val pointsInPixelsInLinearGradient = (shadowSize + innerShadowSize) * point
            val radialGradientStartPoint = outlineCornerRadius - innerShadowSize
            (pointsInPixelsInLinearGradient + radialGradientStartPoint) / outerArcRadius
        }
        val newColorsAndPoints = sideGradientParams
            .colorsAndPoints
            .mapIndexed { index, gradientPointAndValue -> gradientPointAndValue.copy(point = points[index]) }
        return GradientParams(newColorsAndPoints)
    }
}