package com.kt.startkit.domain.usecase

import com.kt.startkit.data.datasource.ItemDataSource
import com.kt.startkit.domain.entity.Item
import com.kt.startkit.domain.mapper.ItemDomainMapper
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ItemUsecase @Inject constructor(
    private val dataSource: ItemDataSource,
    private val domainMapper: ItemDomainMapper,
) : Usecase {

    var id: String = ""

    suspend fun getItems(): List<Item> {
        return dataSource.getItems().map {
            domainMapper(it)
        }
    }
}