package com.kt.startkit.data.datasource

import com.kt.startkit.data.model.PlaceResponse

interface DataSource {
    suspend fun getPlaces() : PlaceResponse
}