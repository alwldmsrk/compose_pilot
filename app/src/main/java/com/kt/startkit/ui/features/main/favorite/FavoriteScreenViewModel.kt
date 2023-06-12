package com.kt.startkit.ui.features.main.setting

import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
) : StateViewModel<SettingViewState>(initialState = SettingViewState.Initial) {

}