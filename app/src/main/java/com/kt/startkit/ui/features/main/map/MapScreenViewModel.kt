package com.kt.startkit.ui.features.main.home

import androidx.lifecycle.viewModelScope
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.domain.usecase.ItemUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val usecase: ItemUsecase
) : StateViewModel<HomeViewState>(initialState = HomeViewState.Initial) {

//    override fun setInitialState(): HomeViewState {
//        return HomeViewState.Initial
//    }

    fun fetchInitialData() {
        viewModelScope.launch {
            updateState { HomeViewState.Loading }
            delay(1000)

            try {
                val items = usecase.getItems()
                updateState { HomeViewState.Data(items) }
            } catch (e: Exception) {
                updateState { HomeViewState.Error("Unknown error") }
            }
        }
    }
}