package com.kt.startkit.ui.features.main.map

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.dataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.domain.entity.FavoriteData
import com.kt.startkit.domain.entity.PlaceData
import com.kt.startkit.domain.usecase.FavoriteUseCase
import com.kt.startkit.core.logger.Logger
import com.kt.startkit.domain.usecase.SearchPlaceUseCase
import com.kt.startkit.ui.features.main.map.CameraRect.Companion.from
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

//    private val placeItems = MutableStateFlow<List<PlaceData>>(emptyList())
//    private val favorites = MutableStateFlow<List<FavoriteData>>(emptyList())
//    private val uiDataStates = combine(
//        placeItems,
//        favorites,
//        ::Pair
//    ).map { (place, favorite) ->
//        Logger.i("combine map test ")
//        updateState { MapViewState.Data(placeItems = place, favorites = favorite) }
//    }.stateIn(
//        scope = viewModelScope
//    )

    /**
     * 검색 장소와 관심 장소를 UIState.Data에 합쳐서 올려보낸다.
     */
    private suspend fun updateUiStateData(
        placeItems: List<PlaceData>? = null,
        favoriteItems: List<FavoriteData>? = null,
    ) {
        val currentState = viewState.value
        if (currentState is MapViewState.Data) {
            updateState { combineStateData(currentState, placeItems, favoriteItems) }
        } else {
            updateState {
                MapViewState.Data(
                    placeItems = placeItems ?: emptyList(),
                    favorites = favoriteItems ?: emptyList()
                )
            }
        }
    }


    /**
     * Uistate.Data를 보낼 때 이전의 값에 덮어 씌워 보내준다.
     */
    private fun combineStateData(
        state: MapViewState.Data,
        placeItems: List<PlaceData>?,
        favorites: List<FavoriteData>?,
    ): MapViewState.Data {
        return MapViewState.Data(
            placeItems ?: state.placeItems,
            favorites ?: state.favorites,
        )
    }

    fun fetchInitialData() {
        viewModelScope.launch {
            launch { initUiState() }
            launch { loadFavoriteItems() }
        }
    }

    private suspend fun initUiState() {
        updateState { MapViewState.Loading }
        try {
            updateUiStateData(placeItems = emptyList())
        } catch (e: Exception) {
            updateState { MapViewState.Error("Unknown error") }
        }
    }

    private suspend fun loadFavoriteItems() {
        favoriteUseCase.getAllFavorites()
            .collect { favorites ->
                updateUiStateData(favoriteItems = favorites)
            }
    }


    /**
     * Search Text 설정
     */
    fun setSearchText(textState: String) {
        _searchText.value = textState
    }


    /**
     * Composable에서 발생한 UiEvent를 ViewModel로 전달하기 위한 메서드
     */
    fun sendUiAction(event: UiAction) {
        when (event) {
            is UiAction.CameraChange -> {
                currentRect = CameraRect(event.left, event.top, event.right, event.bottom)
            }

            is UiAction.SearchPlace -> {
                requestSearchPlace(MapViewState.Initial)
            }

            is UiAction.AddFavoritePlace -> {
                Logger.i("AddFavoritePlace Marker : $event")
                viewModelScope.launch {
                    favoriteUseCase.addFavorite(lat = event.lat, lng = event.lng, name = event.name, address = event.address)
                }
            }
        }
    }


    /**
     * 장소 검색 호출
     */
    private fun requestSearchPlace(isInitial: MapViewState) {
        viewModelScope.launch {
            try {
                // TODO 로딩 화면 추가
                //updateState { MapViewState.Loading }
                if (currentRect != null) {
                    val images =
                        placeUseCase.getPlaces(query = searchText, rect = from(currentRect!!))
                    updateState { MapViewState.Data(images, emptyList()) }
                }
            } catch (e: Exception) {
                updateState { MapViewState.Error("검색에 실패하였습니다.") }
            }
        }
    }

}

data class CameraRect(
    val left: Double,
    val top: Double,
    val right: Double,
    val bottom: Double
) {
    companion object {
        fun from(currentRect: CameraRect): String {
            return with(currentRect) {
                "$left,$top,$right,$bottom"
            }

        }
    }
}


/**
 * Composable로부터 전달 받을 UiAction의 규격 정의
 */
sealed class UiAction {
    data class CameraChange(
        val left: Double,
        val top: Double,
        val right: Double,
        val bottom: Double
    ) : UiAction()

    object SearchPlace : UiAction()
    data class AddFavoritePlace(
        val lat: Double,
        val lng: Double,
        val name: String,
        val address: String,
    ) : UiAction()
}

