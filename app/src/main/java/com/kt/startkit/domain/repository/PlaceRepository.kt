package com.kt.startkit.domain.repository

import com.kt.startkit.data.datasource.PlaceDataSource
import com.kt.startkit.domain.entity.PlaceData
import com.kt.startkit.domain.mapper.PlaceDomainMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class PlaceRepository @Inject constructor(
    private val dataSource: PlaceDataSource,
    private val domainMapper: PlaceDomainMapper,
) : Repository {

    override suspend fun getPlaces(query: String, rect: String): List<PlaceData> {
        return dataSource.getPlaces(query,rect).documents.map {
            domainMapper(it)
        }
    }
}

