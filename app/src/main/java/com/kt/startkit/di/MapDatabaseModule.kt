package com.kt.startkit.di

import android.content.Context
import androidx.room.Room
import com.kt.startkit.data.local.MapDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MapDatabaseModule {
    @Singleton
    @Provides
    fun provideMapDataBase(
        @ApplicationContext context: Context,
    ): MapDatabase = Room.databaseBuilder(
        context,
        MapDatabase::class.java,
        "map.db"
    )
        .fallbackToDestructiveMigration()
        .build()
}