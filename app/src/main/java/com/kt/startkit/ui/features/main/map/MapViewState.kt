package com.kt.startkit.ui.features.main.map

import com.kt.startkit.core.base.ViewState
import com.kt.startkit.domain.entity.Item
import com.kt.startkit.domain.entity.PlaceData

sealed class MapViewState: ViewState() {
    object Initial: MapViewState()
    object Loading: MapViewState()
    data class Data(val items: List<PlaceData>): MapViewState()
    data class Error(val message: String): MapViewState()
}