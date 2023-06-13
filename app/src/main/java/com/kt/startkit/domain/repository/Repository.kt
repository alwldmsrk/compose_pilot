package com.kt.startkit.domain.repository

import com.kt.startkit.domain.entity.PlaceData

interface Repository {
    suspend fun getPlaces(query: String, rect: String): List<PlaceData>
}