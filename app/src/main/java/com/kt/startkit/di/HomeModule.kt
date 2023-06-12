package com.kt.startkit.di

import com.kt.startkit.data.ApiService
import com.kt.startkit.data.datasource.ItemDataSource
import com.kt.startkit.domain.mapper.ItemDomainMapper
import com.kt.startkit.domain.usecase.ItemUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {
//    @Provides
//    fun provideItemDataSource(apiService: ApiService): ItemDataSource {
//        return ItemDataSource(apiService)
//    }
//
//    @Provides
//    fun provideItemUsecase(dataSource: ItemDataSource,
//                           domainMapper: ItemDomainMapper
//    ): ItemUsecase {
//        return ItemUsecase(dataSource, domainMapper)
//    }
}

