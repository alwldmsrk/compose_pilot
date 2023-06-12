package com.kt.startkit.ui.features.main.root

import androidx.lifecycle.viewModelScope
import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootScreenViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
) : StateViewModel<RootViewState>(initialState = RootViewState.Initial) {

    fun observeUserProfile() {
        viewModelScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userProfileRepository.profile
                    .onEach {
                        if (it == null) {
                            updateState { RootViewState.Error("Fail to load userProfile!!") }
                        } else {
                            updateState { RootViewState.Data(userProfile = it) }
                        }
                    }
                    .collect()
//            }
        }
    }
}