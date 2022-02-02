package com.example.composeshadows.ui.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp
import com.example.composeshadows.entity.CustomShadowParams
import com.example.composeshadows.ui.util.ComposeCompatShadowsRenderer

fun Modifier.roundRectShadow(
    customShadowParams: CustomShadowParams,
    cornerRadius: Dp
) = this.then(ShadowDrawer(customShadowParams, cornerRadius))

private class ShadowDrawer(
    private val customShadowParams: CustomShadowParams,
    private val cornerRadius: Dp
) : DrawModifier {

    private val composeCompatShadowsRenderer = ComposeCompatShadowsRenderer()

    override fun ContentDrawScope.draw() {
        customShadowParams.layers.forEach {
            composeCompatShadowsRenderer.paintCompatShadow(
                canvas = this,
                outlineCornerRadius = cornerRadius.toPx(),
                shadow = it
            )
        }
        drawContent()
    }
}
