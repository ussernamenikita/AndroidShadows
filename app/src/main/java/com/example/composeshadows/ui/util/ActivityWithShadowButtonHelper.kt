package com.example.composeshadows.ui.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.composeshadows.entity.ActivityWithShadowButtonParams
import com.example.composeshadows.entity.CustomShadowParams

private const val BUTTON_WIDTH_DP_ARG = "BUTTON_WIDTH_ARG"
private const val BUTTON_HEIGHT_DP_ARG = "BUTTON_HEIGHT_ARG"
private const val BUTTON_RADIUS_DP_ARG = "BUTTON_RADIUS_ARG"
private const val BUTTON_COLOR_ARG = "BUTTON_COLOR_ARG"
private const val SHADOW_PARAMS_ARG = "SHADOW_PARAMS_ARG"

private const val BUTTON_WIDTH_DP = 312
private const val BUTTON_HEIGHT_DP = 100
private const val BUTTON_RADIUS_DP = 20
private const val BUTTON_COLOR_COMPAT = android.graphics.Color.WHITE
private val CUSTOM_SHADOW_PARAMS = CustomShadowParams.shadow5()

object ActivityWithShadowButtonHelper {

    fun pack(
        context: Context,
        widthDp: Int = BUTTON_WIDTH_DP,
        heightDp: Int = BUTTON_HEIGHT_DP,
        cornerRadiusDp: Int = BUTTON_RADIUS_DP,
        buttonColor: Int = BUTTON_COLOR_COMPAT,
        shadowParams: CustomShadowParams = CUSTOM_SHADOW_PARAMS,
        activityClass: Class<*>
    ): Intent {
        val bundle = Bundle().apply {
            putInt(BUTTON_WIDTH_DP_ARG, widthDp)
            putInt(BUTTON_HEIGHT_DP_ARG, heightDp)
            putInt(BUTTON_RADIUS_DP_ARG, cornerRadiusDp)
            putInt(BUTTON_COLOR_ARG, buttonColor)
            putParcelable(SHADOW_PARAMS_ARG, shadowParams)
        }
        val intent = Intent(context, activityClass)
        intent.putExtras(bundle)
        return intent
    }

    fun unpack(activity: Activity): ActivityWithShadowButtonParams {
        var widthDp = BUTTON_WIDTH_DP
        var heightDp = BUTTON_HEIGHT_DP
        var radiusDp = BUTTON_RADIUS_DP
        var color = BUTTON_COLOR_COMPAT
        var shadowParams = CUSTOM_SHADOW_PARAMS
        activity.intent.extras?.let {
            widthDp = it.getInt(
                BUTTON_WIDTH_DP_ARG,
                BUTTON_WIDTH_DP
            )
            heightDp = it.getInt(
                BUTTON_HEIGHT_DP_ARG,
                BUTTON_HEIGHT_DP
            )
            radiusDp = it.getInt(
                BUTTON_RADIUS_DP_ARG,
                BUTTON_HEIGHT_DP
            )
            color = it.getInt(
                BUTTON_COLOR_ARG,
                BUTTON_COLOR_COMPAT
            )
            shadowParams = it.getParcelable(SHADOW_PARAMS_ARG) ?: CUSTOM_SHADOW_PARAMS
        }
        return ActivityWithShadowButtonParams(
            buttonWidthDp = widthDp,
            buttonHeightDp = heightDp,
            shadowParams = shadowParams,
            buttonCornerRadiusDp = radiusDp,
            buttonColor = color
        )
    }
}