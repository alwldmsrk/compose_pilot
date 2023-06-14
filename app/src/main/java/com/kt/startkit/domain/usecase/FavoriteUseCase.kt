package com.kt.startkit.domain.usecase

import com.kt.startkit.data.datasource.FavoriteDataSource
import com.kt.startkit.domain.entity.FavoriteData
import com.kt.startkit.domain.mapper.FavoriteDomainMapper
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class FavoriteUseCase @Inject constructor(
    private val dataSource: FavoriteDataSource,
    private val domainMapper: FavoriteDomainMapper
) : Usecase {

    suspend fun getAllFavorites(): Flow<List<FavoriteData>> {
        return dataSource.getFavoriteModels()
            .map {models ->
                models.map{
                    domainMapper(it)
                }
            }
    }

    suspend fun removeFavorite(name: String) {
        dataSource.removeFavoriteModel(name)
    }

    suspend fun addFavorite(
        lat: Double,
        lng: Double,
        name: String,
        address: String,
    ) {
        dataSource.addFavoriteModel(
            lat = lat,
            lng = lng,
            name = name,
            address = address
        )
    }
}