package com.kt.startkit.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kt.startkit.data.model.FavoriteModel
import com.kt.startkit.domain.entity.FavoriteData
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(model: FavoriteModel)

    @Query("DELETE FROM FavoriteModel WHERE name = :name")
    suspend fun deleteFavorite(name: String)

    @Query("SELECT * FROM FavoriteModel")
     fun loadAllFavorites(): Flow<List<FavoriteModel>>

    @Query("SELECT * FROM FavoriteModel")
    fun loadAllFavoritesWithPaging(): PagingSource<Int, FavoriteModel>
}