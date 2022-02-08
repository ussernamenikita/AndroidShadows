package com.example.composeshadows.entity

import android.graphics.Color
import android.os.Parcelable
import androidx.core.graphics.ColorUtils
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

/**
 * Параметры градиента
 *
 * @param colorsAndPoints - список точек градиента со значением цветов в этой точке
 */
@Parcelize
class GradientParams(
    val colorsAndPoints: List<GradientPointAndColorMultiplier>
) : Parcelable {

    companion object {
        fun defaultLinearGradient(): GradientParams {
            return GradientParams(
                listOf(
                    GradientPointAndColorMultiplier(0f, 1f),
                    GradientPointAndColorMultiplier(0.75f, 0.12f),
                    GradientPointAndColorMultiplier(1f, 0f),
                )
            )
        }
    }

    @IgnoredOnParcel
    private var resultArray: IntArray = IntArray(colorsAndPoints.size)

    @IgnoredOnParcel
    private var points: FloatArray = colorsAndPoints.map { it.point }.toFloatArray()

    fun getColors(color: Int): IntArray {
        return colorsAndPoints.fromColor(color, resultArray)
    }

    fun getPoints(): FloatArray {
        return points
    }

    private fun List<GradientPointAndColorMultiplier>.fromColor(color: Int, resultArray: IntArray): IntArray {
        if (this.size != resultArray.size) {
            throw IllegalArgumentException("Multiplier size and result color size must be equals")
        }
        forEachIndexed { index, pointAndMultiplier ->
            val multiplier = pointAndMultiplier.colorMultiplier
            val startColorA = Color.alpha(color).toFloat()
            val currentColorAlpha = (startColorA * multiplier).roundToInt()
            resultArray[index] = ColorUtils.setAlphaComponent(color, currentColorAlpha)
        }
        return resultArray
    }
}
