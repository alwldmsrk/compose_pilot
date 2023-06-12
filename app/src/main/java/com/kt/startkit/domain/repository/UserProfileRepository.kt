package com.kt.startkit.domain.repository

import com.kt.startkit.core.datastore.PreferenceDataStore
import com.kt.startkit.domain.entity.UserProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class UserProfileRepository(
    private val preferences: PreferenceDataStore,
    private val dispatcher: CoroutineDispatcher,
) : Repository {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()

    suspend fun fetchProfile() {
        CoroutineScope(dispatcher + SupervisorJob()).async {
            delay(3000)
            _profile.emit(UserProfile(id = "id1234", name = "우당탕탕"))
        }.await()
    }

    suspend fun fetchProfileWithException() {
        CoroutineScope(dispatcher + SupervisorJob()).async {
            delay(3000)
            throw Exception()
        }.await()
    }

    fun clear() {
        CoroutineScope(dispatcher + SupervisorJob()).launch {
            _profile.emit(null)
        }
    }
}
