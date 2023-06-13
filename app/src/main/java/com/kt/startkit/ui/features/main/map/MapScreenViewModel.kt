package com.kt.startkit.ui.features.main.map

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.data.ApiService
import com.kt.startkit.domain.repository.PlaceRepository
import com.kt.startkit.core.logger.Logger
import com.kt.startkit.domain.usecase.ItemUsecase
import com.kt.startkit.domain.usecase.SearchPlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val placeUseCase: SearchPlaceUseCase,
    private val usecase: ItemUsecase
) : StateViewModel<MapViewState>(initialState = MapViewState.Initial) {

    /**
     * 검색할 단어
     */
    private val _searchText = mutableStateOf("")
    val searchText get() = _searchText.value



    private var currentRect: CameraRect? = null

    fun fetchInitialData() {
        viewModelScope.launch {
            updateState { MapViewState.Loading }
            delay(1000)

            try {
                val items = usecase.getItems()
                updateState { MapViewState.Data(items) }
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
    fun getSearchPlaces() = requestItems(true)

    private fun requestItems(isInitial: Boolean = false) {
        viewModelScope.launch {
            try {
                val images = placeUseCase.getPlaces(query = searchText)
                Logger.d(images.get(0).toString(),"miji")
            } catch (e: Exception) {
                updateState { MapViewState.Error("Unknown error") }
            }
        }
    }

    fun occurUiEvent(event: UiEvent) {
        when(event) {
            is UiEvent.CameraChange -> {
                currentRect = CameraRect(event.left, event.top, event.right, event.bottom)
            }
        }
    }

    data class CameraRect(val left: Double, val top: Double, val right: Double, val bottom: Double)
}

sealed class UiEvent {
    data class CameraChange(val left: Double, val top: Double, val right: Double, val bottom: Double) : UiEvent()
}