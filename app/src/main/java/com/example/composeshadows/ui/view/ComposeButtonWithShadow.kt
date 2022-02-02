package com.example.composeshadows.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.composeshadows.entity.CustomShadowParams
import com.example.composeshadows.ui.modifier.roundRectShadow

@Composable
fun ComposeButtonWithShadow(
    buttonHeightDp: Float,
    buttonWidthDp: Float,
    shadowParams: CustomShadowParams,
    buttonCornerRadiusDp: Float,
    buttonColor: Color
) {
    Box(
        modifier = Modifier
            .width(Dp(buttonWidthDp))
            .height(Dp(buttonHeightDp))
            .roundRectShadow(
                customShadowParams = shadowParams,
                cornerRadius = Dp(buttonCornerRadiusDp)
            )
            .background(
                color = buttonColor,
                shape = RoundedCornerShape(
                    topStart = Dp(buttonCornerRadiusDp),
                    topEnd = Dp(buttonCornerRadiusDp),
                    bottomEnd = Dp(buttonCornerRadiusDp),
                    bottomStart = Dp(buttonCornerRadiusDp),
                )
            )
    )
}