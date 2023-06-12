package com.kt.startkit.domain.mapper

import com.kt.startkit.data.model.ItemModel
import com.kt.startkit.domain.entity.Item
import com.kt.startkit.domain.entity.ItemCategory
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ItemDomainMapper @Inject constructor() : Mapper<ItemModel, Item> {
    override fun invoke(model: ItemModel): Item {
        return Item(
            id = model.id,
            name = model.name,
            price = model.price,
            count = model.count,
            category = ItemCategory.fromValue(model.category)
        )
    }
}