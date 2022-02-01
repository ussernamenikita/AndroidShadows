package com.example.composeshadows.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.composeshadows.entity.GradientParams
import com.example.composeshadows.entity.GradientPointAndColorMultiplier
import com.example.composeshadows.entity.Shadow
import com.example.composeshadows.ui.state.CustomShadowParamsState
import com.example.composeshadows.ui.util.toDp
import com.example.composeshadows.ui.util.toPx

private const val MAX_DX_VALUE = 100f
private const val MAX_DY_VALUE = 100f
private const val MAX_RADIUS_VALUE = 45f

@Composable
fun SettingForTab(mutableState: MutableState<CustomShadowParamsState>) {
    val selectedIndex = mutableState.value.selectedLayerIndex
    val currentLayer =
        mutableState.value.currentShadowParams.layers[selectedIndex]
    val currentShadowParams = mutableState.value.currentShadowParams
    val currentLayers = currentShadowParams.layers
    fun updateLayer(newLayer: Shadow) {
        val newLayers = currentLayers.mapIndexed { index, shadow ->
            if (index == selectedIndex) {
                newLayer
            } else {
                shadow
            }
        }
        mutableState.value = mutableState.value.copy(
            currentShadowParams = currentShadowParams.copy(layers = newLayers)
        )
    }
    when (mutableState.value.layerSelectedTab) {
        CustomShadowParamsState.LayerTab.ColorAlphaTab -> {
            SliderWithValue(currentValue = currentLayer.colorAlphaPercent, onValueChange = {
                updateLayer(currentLayer.copy(colorAlphaPercent = it))
            })
        }
        CustomShadowParamsState.LayerTab.DxTab -> {
            SliderWithValue(
                currentValue = currentLayer.dX.toDp,
                valueRange = 0f..MAX_DX_VALUE,
                onValueChange = {
                    updateLayer(currentLayer.copy(dX = it.toPx))
                })
        }
        CustomShadowParamsState.LayerTab.DyTab -> {
            SliderWithValue(
                currentValue = currentLayer.dY.toDp,
                valueRange = 0f..MAX_DY_VALUE,
                onValueChange = {
                    updateLayer(currentLayer.copy(dY = it.toPx))
                })
        }
        CustomShadowParamsState.LayerTab.RadiusTab -> {
            SliderWithValue(
                currentValue = currentLayer.radius.toDp,
                valueRange = 0f..MAX_RADIUS_VALUE,
                onValueChange = {
                    updateLayer(currentLayer.copy(radius = it.toPx))
                })
        }
        CustomShadowParamsState.LayerTab.GradientTab -> {
            fun updateGradientParam(
                paramIndex: Int,
                newPointAndMultiplier: GradientPointAndColorMultiplier
            ) {
                val pointsAndColorMultipliers = currentLayer
                    .linearGradientParams
                    .colorsAndPoints
                    .mapIndexed { index, currentPointAndMultiplier ->
                        if (index == paramIndex) {
                            newPointAndMultiplier
                        } else {
                            currentPointAndMultiplier
                        }
                    }
                updateLayer(
                    currentLayer.copy(
                        linearGradientParams = GradientParams(
                            pointsAndColorMultipliers
                        )
                    )
                )
            }
            CenterRow {
                Button(modifier = Modifier.padding(horizontal = Dp(4f)), onClick = {
                    val newParam = GradientPointAndColorMultiplier(1f, 0f)
                    val newPointsAndMultiplier = currentLayer
                        .linearGradientParams
                        .colorsAndPoints
                        .toMutableList()
                        .apply { add(newParam) }
                    updateLayer(
                        currentLayer.copy(
                            linearGradientParams = GradientParams(
                                newPointsAndMultiplier
                            )
                        )
                    )
                }) {
                    Text("+")
                }
                Button(modifier = Modifier.padding(horizontal = Dp(4f)), onClick = {
                    val lastIndex = currentLayer
                        .linearGradientParams
                        .colorsAndPoints.lastIndex
                    val newPointsAndMultiplier = currentLayer
                        .linearGradientParams
                        .colorsAndPoints
                        .toMutableList()
                        .apply { removeAt(lastIndex) }
                    updateLayer(
                        currentLayer.copy(
                            linearGradientParams = GradientParams(
                                newPointsAndMultiplier
                            )
                        )
                    )
                }) {
                    Text("-")
                }
            }
            currentLayer.linearGradientParams.colorsAndPoints.forEachIndexed { index, pointAndColorMultiplier ->
                GradientParamSelector(
                    pointAndColorMultiplier,
                    onPointChanged = {
                        updateGradientParam(
                            index, pointAndColorMultiplier.copy(point = it)
                        )
                    },
                    onColorMultiplierChanged = {
                        updateGradientParam(
                            index, pointAndColorMultiplier.copy(colorMultiplier = it)
                        )
                    }
                )
            }
        }
    }
}