package com.example.composeshadows.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.composeshadows.entity.Shadow
import com.example.composeshadows.ui.state.CustomShadowParamsState

@Composable
fun ShadowParametersSettings(
    mutableState: MutableState<CustomShadowParamsState>
) {
    val currentShadowParams = mutableState.value.currentShadowParams
    val currentLayers = currentShadowParams.layers
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Layer :")
            LayerButtons(mutableState)
            Button(modifier = Modifier.padding(horizontal = Dp(4f)),onClick = {
                val zeroShadow = Shadow.NO_SHADOW
                val newLayers = currentLayers.toMutableList().apply {
                    add(zeroShadow)
                }
                mutableState.value =
                    mutableState.value.copy(currentShadowParams = currentShadowParams.copy(layers = newLayers))
            }) {
                Text("+")
            }
            Button(modifier = Modifier.padding(horizontal = Dp(4f)),onClick = {
                val newLayers = currentLayers.toMutableList().apply {
                    removeAt(currentLayers.lastIndex)
                }
                mutableState.value =
                    mutableState.value.copy(currentShadowParams = currentShadowParams.copy(layers = newLayers))
            }) {
                Text("-")
            }
        }
        TabRow(selectedTabIndex = mutableState.value.selectedTabIndex()) {
            mutableState.value.tabs.forEach {
                Tab(selected = it == mutableState.value.layerSelectedTab, onClick = {
                    mutableState.value = mutableState.value.copy(layerSelectedTab = it)
                }, text = {
                    Text(it.name)
                })
            }
        }
        SettingForTab(mutableState)
    }
}

@Composable
fun LayerButtons(mutableState: MutableState<CustomShadowParamsState>) {
    val size = mutableState.value.currentShadowParams.layers.size
    for (index in 0 until size) {
        Button(modifier = Modifier.padding(horizontal = Dp(4f)), onClick = {
            mutableState.value = mutableState.value.copy(selectedLayerIndex = index)
        }) {
            Text("$index")
        }
    }
}
