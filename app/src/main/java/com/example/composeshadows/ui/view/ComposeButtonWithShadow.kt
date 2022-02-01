package com.example.composeshadows.ui.view

import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.composeshadows.entity.CustomShadowParams
import com.example.composeshadows.ui.util.ComposeCompatShadowsRenderer
import com.example.composeshadows.ui.util.toPx

@Composable
fun ComposeButtonWithShadow(
    buttonHeightDp: Float,
    buttonWidthDp: Float,
    shadowParams: CustomShadowParams,
    buttonCornerRadiusDp: Float,
    buttonColor: Color
) {
    Canvas(
        Modifier.width(Dp(buttonWidthDp)).height(Dp(buttonHeightDp))
    ) {
        ComposeCompatShadowsRenderer().also {
            for (layer in shadowParams.layers) {
                it.outlineCornerRadius = buttonCornerRadiusDp.toPx
                it.outline = RectF(0f, 0f, size.width, size.height)
                it.setShadowParams(layer)
                it.paintCompatShadow(this)
            }
        }
        drawRoundRect(
            color = buttonColor,
            cornerRadius = CornerRadius(buttonCornerRadiusDp.toPx, buttonCornerRadiusDp.toPx)
        )
    }
}