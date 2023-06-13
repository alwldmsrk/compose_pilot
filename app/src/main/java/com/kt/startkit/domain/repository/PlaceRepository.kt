package com.kt.startkit.domain.repository

import com.kt.startkit.data.datasource.PlaceDataSource
import com.kt.startkit.domain.entity.PlaceData
import com.kt.startkit.domain.mapper.PlaceDomainMapper
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@ViewModelScoped
class PlaceRepository @Inject constructor(
    private val dataSource: PlaceDataSource,
    private val domainMapper: PlaceDomainMapper
) : Repository {

    private val _places = MutableStateFlow<List<PlaceData>>(listOf())
    val places = _places.asStateFlow()
    /**
     * 키워드 장소 검색
     */
    override suspend fun getPlaces(): List<PlaceData> {
       return dataSource.getPlaces().documents.map {
           domainMapper(it)
       }
//        CoroutineScope(dispatcher + SupervisorJob()).async {
//            _places.emit(dataSource.getPlaces())
//        }
//        return dataSource.getPlaces()
        TODO("Not yet implemented")
    }

}
