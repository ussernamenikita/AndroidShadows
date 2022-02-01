package com.example.composeshadows.ui.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.composeshadows.entity.CustomShadowParams
import com.example.composeshadows.ui.theme.ComposeShadowsTheme
import com.example.composeshadows.ui.util.ActivityWithShadowButtonHelper
import com.example.composeshadows.ui.util.toPx
import com.example.composeshadows.ui.view.AndroidButtonWithGradientShadow
import com.example.composeshadows.ui.view.ComposeButtonWithShadow

class GradientShadowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawByCompose: MutableState<Boolean> = remember { mutableStateOf(false) }
            ComposeShadowsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ButtonWithShadow(
                        shouldDrawByCompose = drawByCompose,
                        widthDp = params.buttonWidthDp,
                        heightDp = params.buttonHeightDp,
                        shadowParams = params.shadowParams,
                        radiusDp = params.buttonCornerRadiusDp,
                        buttonColor = params.buttonColor
                    )
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(modifier = Modifier.padding(Dp(8f))) {
                            Text(params.shadowParams.name)
                        }
                        Row { DrawByComposeSwitcher(drawByCompose) }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Button(modifier = Modifier.padding(Dp(8f)), onClick = {
                                onCustomizeShadowClicked(params.shadowParams)
                            }) {
                                Text("Customize Shadow")
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DrawByComposeSwitcher(drawByCompose: MutableState<Boolean>) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(Dp(30f))
            ) {
                Text(text = "Draw by Compose")
                Switch(
                    checked = drawByCompose.value,
                    onCheckedChange = { drawByCompose.value = it }
                )
            }
        }
    }

    @Composable
    fun ButtonWithShadow(
        shouldDrawByCompose: State<Boolean>,
        widthDp: Int,
        heightDp: Int,
        shadowParams: CustomShadowParams,
        radiusDp: Int,
        buttonColor: Int
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (shouldDrawByCompose.value) {
                ComposeButtonWithShadow(
                    buttonWidthDp = widthDp.toFloat(),
                    buttonHeightDp = heightDp.toFloat(),
                    shadowParams = shadowParams,
                    buttonCornerRadiusDp = radiusDp.toFloat(),
                    buttonColor = Color(buttonColor)
                )
            } else {
                AndroidButtonWithGradientShadow(
                    width = widthDp.toPx,
                    height = heightDp.toPx,
                    shadowParams = shadowParams,
                    buttonCornerRadius = radiusDp.toPx,
                    buttonColor = buttonColor
                )
            }
        }
    }

    private fun onCustomizeShadowClicked(shadowParams: CustomShadowParams) {
        val intent = ActivityWithShadowButtonHelper.pack(
            context = this,
            shadowParams = shadowParams,
            activityClass = GradientShadowCustomizationActivity::class.java
        )
        startActivity(intent)
    }
}



