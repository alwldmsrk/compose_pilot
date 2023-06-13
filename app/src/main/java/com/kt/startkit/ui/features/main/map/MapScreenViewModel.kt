package com.kt.startkit.ui.features.main.map

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.domain.entity.FavoriteData
import com.kt.startkit.domain.entity.PlaceData
import com.kt.startkit.domain.usecase.FavoriteUseCase
import com.kt.startkit.domain.usecase.ItemUsecase
import com.kt.startkit.domain.usecase.SearchPlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val placeUseCase: SearchPlaceUseCase,
    private val favoriteUseCase: FavoriteUseCase,
) : StateViewModel<MapViewState>(initialState = MapViewState.Initial) {

    /**
     * 검색할 단어
     */
    private val _searchText = mutableStateOf("")
    val searchText get() = _searchText.value

    private var currentRect: CameraRect? = null

    private val placeItems = MutableStateFlow<List<PlaceData>>(emptyList())
    private val favorites = MutableStateFlow<List<FavoriteData>>(emptyList())
    private val uiDataStates = combine(
        placeItems,
        favorites,
        ::Pair
    ).map { (place, favorite) ->
        //todo Logger 잘 찍히는지
        updateState { MapViewState.Data(placeItems = place, favorites = favorite) }
    }

//    private fun combineStateData(
//        state: MapViewState.Data,
//        placeItems: List<PlaceData>?,
//        favorites: List<FavoriteData>?,
//    ): MapViewState.Data {
//        return MapViewState.Data(
//            placeItems ?: state.placeItems,
//            favorites ?: state.favorites,
//        )
//    }

    fun fetchInitialData() {
        viewModelScope.launch {
            updateState { MapViewState.Loading }
            try {
                updateState { MapViewState.Data(emptyList(), emptyList()) }
            } catch (e: Exception) {
                updateState { MapViewState.Error("Unknown error") }
            }
        }
    }


    /**
     * Search Text 설정
     */
    fun setSearchText(textState: String) {
        _searchText.value = textState
    }

    /**
     * 장소 검색 호출
     */
    private fun requestItems(isInitial: Boolean = false) {
        viewModelScope.launch {
            try {
                val images = placeUseCase.getPlaces(query = searchText)
                updateState { MapViewState.Data(images, emptyList()) }
            } catch (e: Exception) {
                updateState { MapViewState.Error("Unknown error") }
            }
        }
    }

    /**
     * Composable에서 발생한 UiEvent를 ViewModel로 전달하기 위한 메서드
     */
    fun sendUiAction(event: UiAction) {
        when (event) {
            is UiAction.CameraChange -> {
                currentRect = CameraRect(event.left, event.top, event.right, event.bottom)
            }

            is UiAction.Search -> {
                requestItems(true)
            }
        }
    }

    data class CameraRect(val left: Double, val top: Double, val right: Double, val bottom: Double)
}

/**
 * Composable로부터 전달 받을 UiAction의 규격 정의
 */
sealed class UiAction {
    data class CameraChange(val left: Double, val top: Double, val right: Double, val bottom: Double) : UiAction()
    object Search : UiAction()
}