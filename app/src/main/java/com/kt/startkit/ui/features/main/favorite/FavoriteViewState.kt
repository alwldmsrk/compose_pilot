package com.kt.startkit.ui.features.main.favorite

import androidx.paging.PagingData
import com.kt.startkit.core.base.ViewState
import com.kt.startkit.domain.entity.FavoriteData
import kotlinx.coroutines.flow.MutableStateFlow

sealed class FavoriteViewState : ViewState() {
    object Loading: FavoriteViewState()
    object Initial : FavoriteViewState()
    object Success : FavoriteViewState()
    data class Data(val favoriteItem: MutableStateFlow<PagingData<FavoriteData>>): FavoriteViewState()
    data class Error(val message: String) : FavoriteViewState()
}