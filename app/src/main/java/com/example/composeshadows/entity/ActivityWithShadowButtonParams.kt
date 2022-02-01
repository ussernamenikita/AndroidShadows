package com.example.composeshadows.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivityWithShadowButtonParams(
    val buttonWidthDp: Int = 0,
    val buttonHeightDp: Int = 0,
    val shadowParams: CustomShadowParams = CustomShadowParams.noShadow(),
    val buttonCornerRadiusDp: Int = 0,
    val buttonColor: Int = 0
) : Parcelable