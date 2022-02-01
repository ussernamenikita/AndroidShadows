package com.example.composeshadows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.composeshadows.entity.CustomShadowParams
import com.example.composeshadows.ui.screen.*
import com.example.composeshadows.ui.theme.ComposeShadowsTheme
import com.example.composeshadows.ui.util.ActivityWithShadowButtonHelper
import com.example.composeshadows.ui.view.CenterRow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeShadowsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CenterRow { Text("Gradient Shadows") }
                        CenterRow { Column { GradientShadows() } }
                        CenterRow { Text("Other Variants") }
                        Row(
                            modifier = Modifier
                                .border(border = BorderStroke(Dp(1f), Color.Black))
                        ) {
                            Column { OtherVariants() }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun OtherVariants() {
        OpenActivityButton(
            "Elevation Shadows",
            ElevationShadowActivity::class.java,
            CustomShadowParams.shadow2()
        )
        OpenActivityButton(
            "MaterialShapeDrawable",
            MaterialShapeDrawableActivity::class.java,
            CustomShadowParams.shadow2()
        )
        OpenActivityButton(
            "ScriptIntrinsicBlurShadow",
            ScriptIntrinsicBlurActivity::class.java,
            CustomShadowParams.shadow2()
        )
        OpenActivityButton(
            "ShadowByShadowLayer",
            ShadowLayerActivity::class.java,
            CustomShadowParams.shadow2()
        )
    }

    @Composable
    fun GradientShadows() {
        CenterRow {
            GradientShadowButton(CustomShadowParams.shadow1())
            GradientShadowButton(CustomShadowParams.shadow2())
            GradientShadowButton(CustomShadowParams.shadow3())
        }
        CenterRow {
            GradientShadowButton(CustomShadowParams.shadow4())
            GradientShadowButton(CustomShadowParams.shadow5())
            GradientShadowButton(CustomShadowParams.shadow6())
        }
        CenterRow {
            GradientShadowButton(CustomShadowParams.shadow7())
        }
    }

    @Composable
    fun OpenActivityButton(
        text: String,
        activityClass: Class<*>,
        shadowParams: CustomShadowParams
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = Dp(8f)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { openActivity(shadowParams, activityClass) }) {
                Text(text)
            }
        }
    }

    @Composable
    fun GradientShadowButton(shadowParams: CustomShadowParams) {
        Button(
            modifier = Modifier.padding(horizontal = Dp(4f)),
            onClick = { onGradientShadowClicked(shadowParams) }) {
            Text(shadowParams.name)
        }
    }

    private fun onGradientShadowClicked(shadowParams: CustomShadowParams) {
        openActivity(shadowParams, GradientShadowActivity::class.java)
    }

    private fun openActivity(
        shadowParams: CustomShadowParams,
        clazz: Class<*>
    ) {
        val intent = ActivityWithShadowButtonHelper.pack(
            context = this,
            shadowParams = shadowParams,
            activityClass = clazz
        )
        startActivity(intent)
    }
}
