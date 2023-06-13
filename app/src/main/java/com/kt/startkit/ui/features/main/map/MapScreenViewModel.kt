package com.kt.startkit.ui.features.main.map

import androidx.lifecycle.viewModelScope
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.core.logger.Logger
import com.kt.startkit.domain.usecase.ItemUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val usecase: ItemUsecase
) : StateViewModel<MapViewState>(initialState = MapViewState.Initial) {

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

    fun occurUiEvent(event: UiEvent) {
        when(event) {
            is UiEvent.CameraChange -> {
                currentRect = CameraRect(event.left, event.top, event.right, event.bottom)
                Logger.i("camera Change occur : left : ${currentRect?.left}")
            }
        }
    }

    data class CameraRect(val left: Double, val top: Double, val right: Double, val bottom: Double)
}

sealed class UiEvent {
    data class CameraChange(val left: Double, val top: Double, val right: Double, val bottom: Double) : UiEvent()
}