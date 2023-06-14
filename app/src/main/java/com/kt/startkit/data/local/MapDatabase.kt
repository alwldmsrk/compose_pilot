package com.kt.startkit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kt.startkit.data.model.FavoriteModel

@Database(entities = [FavoriteModel::class], version = 2)
abstract class MapDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}