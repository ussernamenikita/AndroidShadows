package com.example.composeshadows.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import java.text.DecimalFormat

private const val TEXT_MAX_WIDTH = 100f
private val pattern = "##0.00"
private val decimalFormat = DecimalFormat(pattern)

@Composable
fun SliderWithValue(
    currentValue: Float,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChange: (Float) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.border(
            border = BorderStroke(Dp(1f), Color.Black)
        ).fillMaxWidth()
    ) {
        Text(decimalFormat.format(currentValue), modifier = Modifier.width(Dp(TEXT_MAX_WIDTH)))
        Slider(value = currentValue, valueRange = valueRange, onValueChange = onValueChange)
    }
}