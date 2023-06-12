package com.kt.startkit.ui.features.main.root

import com.kt.startkit.core.base.ViewState
import com.kt.startkit.domain.entity.UserProfile

sealed class RootViewState: ViewState() {
    object Initial: RootViewState()
    data class Data(val userProfile: UserProfile): RootViewState()
    data class Error(val message: String): RootViewState()
}