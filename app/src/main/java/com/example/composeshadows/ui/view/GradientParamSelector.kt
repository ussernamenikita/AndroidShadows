package com.example.composeshadows.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
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
import com.example.composeshadows.entity.GradientPointAndColorMultiplier
import java.text.DecimalFormat

private const val TEXT_MAX_WIDTH_DP = 100f
private val pattern = "##0.00"
private val decimalFormat = DecimalFormat(pattern)

@Composable
fun GradientParamSelector(
    currentValue: GradientPointAndColorMultiplier,
    onPointChanged: (Float) -> Unit,
    onColorMultiplierChanged: (Float) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .border(border = BorderStroke(width = Dp(1f), Color.Black))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Point : ${decimalFormat.format(currentValue.point)}", modifier = Modifier.width(Dp(TEXT_MAX_WIDTH_DP)))
            Slider(
                value = currentValue.point,
                onValueChange = onPointChanged
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Value : ${decimalFormat.format(currentValue.colorMultiplier)}",
                modifier = Modifier.width(Dp(TEXT_MAX_WIDTH_DP))
            )
            Slider(
                value = currentValue.colorMultiplier,
                onValueChange = onColorMultiplierChanged
            )
        }
    }
}