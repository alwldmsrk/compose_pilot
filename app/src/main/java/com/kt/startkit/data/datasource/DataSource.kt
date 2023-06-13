package com.kt.startkit.data.datasource

import com.kt.startkit.data.model.PlaceResponse
import kotlinx.coroutines.flow.MutableStateFlow

interface DataSource {
    suspend fun getPlaces(query: String) : PlaceResponse
}