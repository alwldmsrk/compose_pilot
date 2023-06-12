package com.kt.startkit.ui.features.main.map

import androidx.lifecycle.viewModelScope
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.domain.usecase.ItemUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val usecase: ItemUsecase
) : StateViewModel<MapViewState>(initialState = MapViewState.Initial) {

//    override fun setInitialState(): HomeViewState {
//        return HomeViewState.Initial
//    }

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
}