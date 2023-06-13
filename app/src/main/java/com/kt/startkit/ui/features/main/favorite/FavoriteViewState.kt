package com.kt.startkit.ui.features.main.favorite

import com.kt.startkit.core.base.ViewState
import com.kt.startkit.domain.entity.FavoriteData

sealed class FavoriteViewState : ViewState() {
    object Loading: FavoriteViewState()
    object Initial : FavoriteViewState()
    object Success : FavoriteViewState()
    data class Data(val favoriteItem: List<FavoriteData>): FavoriteViewState()
    data class Error(val message: String) : FavoriteViewState()
}