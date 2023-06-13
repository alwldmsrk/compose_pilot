package com.kt.startkit.di

import com.kt.startkit.core.datastore.PreferenceDataStore
import com.kt.startkit.data.ApiService
import com.kt.startkit.data.datasource.PlaceDataSource
import com.kt.startkit.domain.mapper.PlaceDomainMapper
import com.kt.startkit.domain.repository.PlaceRepository
import com.kt.startkit.domain.usecase.SearchPlaceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object PlaceModule {
    @Provides
    fun providePlaceDataSource(apiService: ApiService): PlaceDataSource {
        return PlaceDataSource(apiService)
    }

    @Provides
    fun providePlaceUseCase(
        repository: PlaceRepository
    ): SearchPlaceUseCase {
        return SearchPlaceUseCase(repository)
    }

    @Provides
    fun providePlaceRepository(
        dataSource: PlaceDataSource,
        domainMapper: PlaceDomainMapper
    ): PlaceRepository {
        return PlaceRepository(dataSource, domainMapper)
    }


//    @Singleton
//    @Provides
//    fun providePlaceRepository(
//        preferences: PreferenceDataStore,
//        @AppDispatchers(AppCoroutineDispatchers.IO) dispatcher: CoroutineDispatcher,
//    ): UserProfileRepository {
//        return UserProfileRepository(
//            preferences = preferences,
//            dispatcher = dispatcher
//        )
//    }
}

