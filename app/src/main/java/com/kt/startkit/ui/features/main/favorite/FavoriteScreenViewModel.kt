package com.kt.startkit.ui.features.main.favorite

import com.kt.startkit.core.base.StateViewModel
import com.kt.startkit.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
) : StateViewModel<FavoriteViewState>(initialState = FavoriteViewState.Initial) {

}