package com.example.composeshadows.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GradientPointAndColorMultiplier(
    val point: Float,
    val colorMultiplier: Float
):Parcelable