package com.kt.startkit.data.datasource

import com.kt.startkit.data.ApiService
import com.kt.startkit.data.model.ItemModel
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ItemDataSource @Inject constructor(
//class ItemDataSource(
    private val userApiService: ApiService,
) : DataSource {
    suspend fun getItems(): List<ItemModel> {
        return listOf(
            ItemModel("000001", "apple", 3000, 100, "fruits"),
            ItemModel("000002", "banana", 1000, 300, "fruits"),
        )
    }
}