package com.kt.startkit.data.datasource

import com.kt.startkit.data.ApiService
import com.kt.startkit.data.model.Document
import com.kt.startkit.data.model.PlaceResponse
import javax.inject.Inject


class PlaceDataSource @Inject constructor(
    private val placeApiService: ApiService
): DataSource {
    override suspend fun getPlaces(query: String): PlaceResponse {
        return placeApiService.getPlaces(query)

    }

}