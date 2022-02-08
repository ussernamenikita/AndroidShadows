package com.example.composeshadows.entity

import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import kotlinx.parcelize.Parcelize

/**
 * Параметры кнопки с тенью
 *
 * @param buttonWidthDp - ширина кнопки
 * @param buttonHeightDp - высота кнопки
 * @param shadowParams - тень
 * @param buttonCornerRadiusDp - радиус закругления углов кнопки
 */
@Parcelize
data class ActivityWithShadowButtonParams(
    @Dimension(unit = Dimension.DP) val buttonWidthDp: Int = 0,
    @Dimension(unit = Dimension.DP) val buttonHeightDp: Int = 0,
    val shadowParams: CustomShadowParams = CustomShadowParams.noShadow(),
    @Dimension(unit = Dimension.DP) val buttonCornerRadiusDp: Int = 0,
    @ColorInt val buttonColor: Int = 0
) : Parcelable