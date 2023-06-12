package com.kt.startkit.di

import android.content.Context
import com.kt.startkit.core.datastore.PreferenceDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun providePrefDataStore(
        @ApplicationContext context: Context,
        @AppDispatchers(AppCoroutineDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): PreferenceDataStore = PreferenceDataStore(context = context, dispatcher = dispatcher)
}