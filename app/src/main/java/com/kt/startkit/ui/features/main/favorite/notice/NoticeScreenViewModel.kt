package com.kt.startkit.ui.features.main.setting.notice

import androidx.lifecycle.ViewModel
import com.kt.startkit.domain.usecase.ItemUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
class NoticeScreenViewModel @Inject constructor(
    private val usecase: ItemUsecase
) : ViewModel() {

}