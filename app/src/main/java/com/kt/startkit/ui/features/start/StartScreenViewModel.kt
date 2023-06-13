package com.kt.startkit.ui.features.start

import androidx.lifecycle.viewModelScope
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.core.logger.Logger
import com.kt.startkit.domain.usecase.LoginUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUsecase
) : StateViewModel<StartScreenState>(initialState = StartScreenState.Loading) {

    fun fetchInitialData(needPermissionCheck: Boolean = false) {
        Logger.d("fetch initial data~~~")
        viewModelScope.launch {
            try {
                if (needPermissionCheck) {
                    updateState { StartScreenState.CheckPermission }
                    return@launch
                }

                if (shouldAppUpdate()) {
                    updateState { StartScreenState.ShouldAppUpdate }
                    return@launch
                }

                if (canAutoLogin()) {

                    updateState {
                        StartScreenState.NavigateToMain(bannerPolicy = false)
                    }
                    return@launch
                }

                updateState { StartScreenState.NeedToLogin }
            } catch (e: Exception) {
                Logger.e("fetching error :${e.message}")
                e.printStackTrace()
                updateState { StartScreenState.FailToInitialize }
            }
        }
    }

    private suspend fun shouldAppUpdate(): Boolean {
        return false
    }

    private suspend fun canAutoLogin(): Boolean {
        return true
    }

}
