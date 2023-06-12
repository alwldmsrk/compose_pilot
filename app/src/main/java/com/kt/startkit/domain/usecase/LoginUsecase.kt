package com.kt.startkit.domain.usecase

import com.kt.startkit.core.datastore.PreferenceDataStore
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@ViewModelScoped
class LoginUsecase @Inject constructor(
    private val preferences: PreferenceDataStore
) : Usecase {

    suspend fun setAutoLogin(isAutoLogIn: Boolean) {
        preferences.updateAutoLogin(autoLogIn = isAutoLogIn)
    }

    suspend fun isAutoLogin(): Boolean {
        return preferences.isAutoLogin().first()
    }

    suspend fun loginIn(id: String, pwd: String) {
        TODO("Not yet implemented")
    }
}