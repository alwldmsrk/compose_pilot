package com.kt.startkit.ui.features.main.home

import com.kt.startkit.core.base.ViewState
import com.kt.startkit.domain.entity.Item

sealed class HomeViewState: ViewState() {
    object Initial: HomeViewState()
    object Loading: HomeViewState()
    data class Data(val items: List<Item>): HomeViewState()
    data class Error(val message: String): HomeViewState()
}