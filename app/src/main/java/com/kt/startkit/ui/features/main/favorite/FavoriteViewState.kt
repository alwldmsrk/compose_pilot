package com.kt.startkit.ui.features.main.favorite

import com.kt.startkit.core.base.ViewState

sealed class FavoriteViewState : ViewState() {
    object Initial : FavoriteViewState()
    object Success : FavoriteViewState()
    data class Error(val message: String) : FavoriteViewState()
}