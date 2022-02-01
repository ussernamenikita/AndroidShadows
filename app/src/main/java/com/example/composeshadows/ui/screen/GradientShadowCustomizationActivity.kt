package com.example.composeshadows.ui.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.composeshadows.ui.state.CustomShadowParamsState
import com.example.composeshadows.ui.view.ComposeButtonWithShadow
import com.example.composeshadows.ui.view.ShadowParametersSettings
import com.example.composeshadows.ui.view.SimpleShadowDemonstrateView

class GradientShadowCustomizationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentShadowParamsState = remember {
                mutableStateOf(CustomShadowParamsState(currentShadowParams = params.shadowParams))
            }
            SimpleShadowDemonstrateView(
                currentShadowParamsState,
                button = {
                    ComposeButtonWithShadow(
                        buttonHeightDp = params.buttonHeightDp.toFloat(),
                        buttonWidthDp = params.buttonWidthDp.toFloat(),
                        shadowParams = it.value.currentShadowParams,
                        buttonCornerRadiusDp = params.buttonCornerRadiusDp.toFloat(),
                        buttonColor = Color(params.buttonColor)
                    )
                },
                shadowSettings = {
                    ShadowParametersSettings(it)
                }
            )
        }
    }
}