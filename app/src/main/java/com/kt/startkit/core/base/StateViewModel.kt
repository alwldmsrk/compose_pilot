package com.kt.startkit.core.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class StateViewModel<UiState : ViewState> constructor(initialState: UiState) :
    ViewModel() {
//    private val initialState: UiState by lazy { setInitialState() }
//    abstract fun setInitialState(): UiState

    private val _state = MutableStateFlow(initialState)
    val viewState = _state.asStateFlow()

    suspend fun updateState(reducer: UiState.() -> UiState) {
        val updatedState = viewState.value.reducer()
        _state.emit(updatedState)
//        _state.value = updatedState
    }
}

@NonRestartableComposable
@Composable
fun<State: ViewState> StateViewModelListener(stateViewModel: StateViewModel<State>,
                                             listen: (State)-> Unit) {

    LaunchedEffect(key1 = "", block = {
        stateViewModel.viewState.collect {
            listen(it)
        }
    })
}
