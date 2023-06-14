package com.kt.startkit.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kt.startkit.data.local.MapDatabase
import com.kt.startkit.data.model.FavoriteModel
import com.kt.startkit.data.model.PlaceResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(
    private val mapDatabase: MapDatabase
) {
     fun getFavoriteModels(): Flow<List<FavoriteModel>> {
        return mapDatabase.favoriteDao().loadAllFavorites()
    }

    fun getFavoriteModelsWithPaging(): Flow<PagingData<FavoriteModel>> {
        val pagingSourceFactory = { mapDatabase.favoriteDao().loadAllFavoritesWithPaging() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    suspend fun removeFavoriteModel(name: String) {
        mapDatabase.favoriteDao().deleteFavorite(name)
    }

    suspend fun addFavoriteModel(
        lat: Double,
        lng: Double,
        name: String,
        address: String,
        url: String
    ) {
        mapDatabase.favoriteDao().insertFavorite(
            FavoriteModel(
                lat = lat,
                lng = lng,
                name = name,
                address = address,
                url = url
            )
        )
    }
}