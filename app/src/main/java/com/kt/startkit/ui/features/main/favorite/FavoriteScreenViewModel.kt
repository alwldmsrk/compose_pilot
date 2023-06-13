package com.kt.startkit.ui.features.main.favorite

import androidx.lifecycle.viewModelScope
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.domain.entity.FavoriteData


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    //private val userProfileRepository: UserProfileRepository,
) : StateViewModel<FavoriteViewState>(initialState = FavoriteViewState.Initial) {


    val favoriteList: List<FavoriteData> = listOf(FavoriteData(0.0,0.0,"안경점","태봉로 151-1"))


    // TODO Screen 에서 호출 (임시로 데이터 추가)
    fun getFavoriteList() {
        viewModelScope.launch {
            updateState { FavoriteViewState.Loading }
            try {
                updateState { FavoriteViewState.Data(favoriteList) }
            } catch (e: Exception) {
                updateState { FavoriteViewState.Error("Unknown error") }
            }
        }
    }

    fun sendUiAction(event: UiAction) {
        when (event) {
            is UiAction.DeleteBookMark -> {
                //
            }
        }
    }

    /**
     * Composable로부터 전달 받을 UiAction의 규격 정의
     */
    sealed class UiAction {
        object DeleteBookMark : UiAction()
    }

}