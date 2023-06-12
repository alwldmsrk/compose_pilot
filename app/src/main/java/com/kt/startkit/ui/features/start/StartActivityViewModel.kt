package com.kt.startkit.ui.features.start

import androidx.lifecycle.viewModelScope
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.core.logger.Logger
import com.kt.startkit.domain.repository.UserProfileRepository
import com.kt.startkit.domain.usecase.LoginUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StartActivityViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val userProfileRepository: UserProfileRepository,
) : StateViewModel<StartActivityState>(initialState = StartActivityState.Loading) {

    fun fetchInitialData() {
        Logger.d("fetch initial data~~~")
        viewModelScope.launch {
            try {
                if (shouldAppUpdate()) {
                    updateState { StartActivityState.ShouldAppUpdate }
                    return@launch
                }

                if (canAutoLogin()) {
                    userProfileRepository.fetchProfile()
                    updateState {
                        StartActivityState.NavigateToMain(bannerPolicy = false)
                    }
                    return@launch
                }

                updateState { StartActivityState.NeedToLogin }
            } catch (e: Exception) {
                Logger.e("fetching error :${e.message}")
                e.printStackTrace()
                updateState { StartActivityState.FailToInitialize }
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

