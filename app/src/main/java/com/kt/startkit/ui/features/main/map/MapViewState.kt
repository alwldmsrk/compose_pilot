package com.kt.startkit.ui.features.main.map

import com.kt.startkit.core.base.ViewState
import com.kt.startkit.domain.entity.Item

sealed class MapViewState: ViewState() {
    object Initial: MapViewState()
    object Loading: MapViewState()
    data class Data(val items: List<Item>): MapViewState()
    data class Error(val message: String): MapViewState()
}