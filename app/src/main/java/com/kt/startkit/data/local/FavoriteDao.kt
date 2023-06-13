package com.kt.startkit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kt.startkit.data.model.FavoriteModel

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(model: FavoriteModel)

    @Query("DELETE FROM FavoriteModel WHERE name = :name")
    suspend fun deleteFavorite(name: String)

    @Query("SELECT * FROM FavoriteModel")
    suspend fun loadAllFavorites(): List<FavoriteModel>
}