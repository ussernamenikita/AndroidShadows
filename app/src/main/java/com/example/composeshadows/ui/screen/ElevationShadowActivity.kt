package com.example.composeshadows.ui.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.composeshadows.ui.state.CustomShadowParamsState
import com.example.composeshadows.ui.util.toPx
import com.example.composeshadows.ui.view.AndroidViewWithSDKShadow
import com.example.composeshadows.ui.view.SimpleShadowDemonstrateView

class ElevationShadowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentShadowParamsState = remember {
                mutableStateOf(
                    CustomShadowParamsState(
                        currentShadowParams = params.shadowParams,
                        tabs = listOf(
                            CustomShadowParamsState.LayerTab.DxTab,
                            CustomShadowParamsState.LayerTab.DyTab,
                            CustomShadowParamsState.LayerTab.RadiusTab
                        )
                    )
                )
            }
            SimpleShadowDemonstrateView(
                currentShadowParamsState,
                button = {
                    AndroidViewWithSDKShadow(
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