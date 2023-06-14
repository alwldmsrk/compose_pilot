package com.kt.startkit.ui.features.main.favorite

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.domain.entity.FavoriteData
import com.kt.startkit.domain.usecase.FavoriteUseCase
import com.kt.startkit.ui.features.main.map.UiAction


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase
) : StateViewModel<FavoriteViewState>(initialState = FavoriteViewState.Initial) {

    private val _favoriteListWithPaging =
        MutableStateFlow<PagingData<FavoriteData>>(PagingData.empty())
    private val favoriteListWithPaging = _favoriteListWithPaging


    /**
     * Compose Paging3 라이브러리를 사용하는 경우
     */
    private fun getFavoriteListWithPaging() {
        viewModelScope.launch {
            updateState { FavoriteViewState.Loading }
            try {
                favoriteUseCase.getAllFavoritesWithPaging()
                    .cachedIn(viewModelScope).collect {
                        _favoriteListWithPaging.value = it
                        updateState { FavoriteViewState.Data(favoriteListWithPaging) }
                    }
            } catch (e: Exception) {
                updateState { FavoriteViewState.Error("Unknown error") }
            }
        }
    }


    /**
     * 북마크 삭제 호출
     */
    private fun requestDeleteBookmark(name: String) {
        viewModelScope.launch {
            favoriteUseCase.removeFavorite(name)
        }
    }


    fun sendUiAction(event: UiAction) {
        when (event) {
            is UiAction.DeleteBookmark -> {
                requestDeleteBookmark(event.name)
            }
            is UiAction.GetBookmark -> {
                getFavoriteListWithPaging()
            }
        }
    }


    /**
     * Composable로부터 전달 받을 UiAction의 규격 정의
     */
    sealed class UiAction {
        data class DeleteBookmark(val name: String) : UiAction()
        object GetBookmark : UiAction()
    }
}




