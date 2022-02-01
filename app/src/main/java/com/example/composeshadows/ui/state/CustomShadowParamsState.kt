package com.example.composeshadows.ui.state

import com.example.composeshadows.entity.CustomShadowParams

data class CustomShadowParamsState(
    val selectedLayerIndex: Int = 0,
    val layerSelectedTab: LayerTab = LayerTab.DxTab,
    val currentShadowParams: CustomShadowParams,
    val tabs: List<LayerTab> = allTabs
) {
    fun selectedTabIndex(): Int {
        return tabs.indexOf(layerSelectedTab)
    }

    companion object {
        const val DX_TAB = "Dx"
        const val DY_TAB = "Dy"
        const val RADIUS_TAB = "Radius"
        const val COLOR_ALPHA_TAB = "Color Alpha"
        const val GRADIENT_TAB = "Gradient"

        private val allTabs = listOf(
            LayerTab.DxTab,
            LayerTab.DyTab,
            LayerTab.RadiusTab,
            LayerTab.ColorAlphaTab,
            LayerTab.GradientTab,
        )
    }

    sealed class LayerTab(val name: String) {
        object DxTab : LayerTab(DX_TAB)
        object DyTab : LayerTab(DY_TAB)
        object RadiusTab : LayerTab(RADIUS_TAB)
        object ColorAlphaTab : LayerTab(COLOR_ALPHA_TAB)
        object GradientTab : LayerTab(GRADIENT_TAB)
    }
}
