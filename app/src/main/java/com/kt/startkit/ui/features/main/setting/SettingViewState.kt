package com.kt.startkit.ui.features.main.setting

import com.kt.startkit.core.base.ViewState

sealed class SettingViewState : ViewState() {
    object Initial : SettingViewState()
    object Success : SettingViewState()
    data class Error(val message: String) : SettingViewState()
}