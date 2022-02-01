package com.example.composeshadows.ui.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.composeshadows.ui.state.CustomShadowParamsState
import com.example.composeshadows.ui.util.toPx
import com.example.composeshadows.ui.view.ShadowsByShadowLayerView
import com.example.composeshadows.ui.view.SimpleShadowDemonstrateView

class ShadowLayerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentShadowParamsState = remember {
                mutableStateOf(
                    CustomShadowParamsState(
                        currentShadowParams = params.shadowParams,
                        tabs = listOf(
                            CustomShadowParamsState.LayerTab.RadiusTab,
                            CustomShadowParamsState.LayerTab.DxTab,
                            CustomShadowParamsState.LayerTab.DyTab,
                            CustomShadowParamsState.LayerTab.ColorAlphaTab
                        ),
                        layerSelectedTab = CustomShadowParamsState.LayerTab.RadiusTab
                    )
                )
            }
            SimpleShadowDemonstrateView(
                currentShadowParamsState,
                button = {
                    ShadowsByShadowLayerView(
                        width = params.buttonWidthDp.toPx,
                        height = params.buttonHeightDp.toPx,
                        state = it,
                        buttonCornerRadius = params.buttonCornerRadiusDp.toPx,
                        buttonColor = params.buttonColor
                    )
                }
            )
        }
    }
}