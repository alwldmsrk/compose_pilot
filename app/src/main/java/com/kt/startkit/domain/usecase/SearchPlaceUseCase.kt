package com.kt.startkit.domain.usecase

import com.kt.startkit.data.datasource.PlaceDataSource
import com.kt.startkit.domain.entity.PlaceData
import com.kt.startkit.domain.mapper.PlaceDomainMapper
import com.kt.startkit.domain.repository.PlaceRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SearchPlaceUseCase @Inject constructor(
    private val repository: PlaceRepository
) : Usecase {

    suspend fun getPlaces(): List<PlaceData> {
       return repository.getPlaces()
    }
}