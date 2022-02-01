package com.example.composeshadows.entity

import android.graphics.Color
import android.os.Parcelable
import com.example.composeshadows.ui.util.toPx
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomShadowParams(
    val name: String,
    val layers: List<Shadow>
) : Parcelable {
    companion object {

        const val SHADOW_COLOR = Color.BLACK

        fun shadow1(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow1",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 1.toPx,
                        radius = 2.toPx,
                        color = SHADOW_COLOR,
                        colorAlphaPercent = 0.18f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.75f, 0.12f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        fun shadow2(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow2",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 2.toPx,
                        radius = 9.toPx,
                        color = SHADOW_COLOR,
                        colorAlphaPercent = 0.14f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 0.6f),
                                GradientPointAndColorMultiplier(0.75f, 0.10f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        fun shadow3(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow3",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 4.toPx,
                        radius = 10.toPx,
                        color = SHADOW_COLOR,
                        colorAlphaPercent = 0.18f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 0.65f),
                                GradientPointAndColorMultiplier(0.75f, 0.05f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        fun shadow4(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow4",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 4.toPx,
                        radius = 8.toPx,
                        color = SHADOW_COLOR,
                        colorAlphaPercent = 0.27f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.8f, 0.15f),
                                GradientPointAndColorMultiplier(1f, 0f),
                            )
                        )
                    )
                )
            )
        }

        fun shadow5(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow5",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 7.toPx,
                        radius = 12.toPx,
                        color = SHADOW_COLOR,
                        colorAlphaPercent = 0.22f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.84f, 0.05f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        fun shadow6(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow6",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 3.toPx,
                        radius = 5.toPx,
                        color = SHADOW_COLOR,
                        colorAlphaPercent = 0.27f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.85f, 0.1f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    ),
                    Shadow(
                        dX = 0.toPx,
                        dY = 1.toPx,
                        radius = 14.toPx,
                        color = SHADOW_COLOR,
                        colorAlphaPercent = 0.15f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.85f, 0.1f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        fun shadow7(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow7",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 11.toPx,
                        radius = 15.toPx,
                        colorAlphaPercent = 0.2f,
                        color = SHADOW_COLOR,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 01f),
                                GradientPointAndColorMultiplier(0.8f, 0.05f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    ),
                    Shadow(
                        dX = 0.toPx,
                        dY = 9.toPx,
                        radius = 35.toPx,
                        colorAlphaPercent = 0.15f,
                        color = SHADOW_COLOR,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.8f, 0.05f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    ),
                    Shadow(
                        dX = 0.toPx,
                        dY = 24.toPx,
                        radius = 38.toPx,
                        colorAlphaPercent = 0.08f,
                        color = SHADOW_COLOR,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 0.8f),
                                GradientPointAndColorMultiplier(0.46f, 0.3f),
                                GradientPointAndColorMultiplier(0.75f, 0.1f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        fun noShadow(): CustomShadowParams =
            CustomShadowParams(
                name = "NoShadow",
                listOf(
                    Shadow(
                        dX = 0f,
                        dY = 0f,
                        radius = 0f,
                        color = Color.TRANSPARENT,
                        linearGradientParams = GradientParams.defaultLinearGradient(),
                        colorAlphaPercent = 0f,
                    )
                )
            )
    }
}