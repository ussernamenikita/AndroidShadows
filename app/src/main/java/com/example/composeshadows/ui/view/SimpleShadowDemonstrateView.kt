package com.example.composeshadows.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.composeshadows.ui.state.CustomShadowParamsState
import com.example.composeshadows.ui.theme.ComposeShadowsTheme

@Composable
fun SimpleShadowDemonstrateView(
    mutableState: MutableState<CustomShadowParamsState>,
    button: @Composable RowScope.(MutableState<CustomShadowParamsState>) -> Unit,
    shadowSettings: @Composable RowScope.(MutableState<CustomShadowParamsState>) -> Unit = {
        SimpleShadowParametersSettings(it)
    }
) {
    ComposeShadowsTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = Dp(10f)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(mutableState.value.currentShadowParams.name)
                }
                Row {
                    button(mutableState)
                }
                Row(modifier = Modifier.padding(top = Dp(48f))) {
                    shadowSettings(mutableState)
                }
            }
        }
    }
}

@Composable
private fun SimpleShadowParametersSettings(
    mutableState: MutableState<CustomShadowParamsState>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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