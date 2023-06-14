package com.kt.startkit.domain.mapper

import com.kt.startkit.data.model.FavoriteModel
import com.kt.startkit.domain.entity.FavoriteData
import javax.inject.Inject

class FavoriteDomainMapper @Inject constructor() : Mapper<FavoriteModel, FavoriteData> {
    override fun invoke(model: FavoriteModel): FavoriteData {
        return FavoriteData(
            lng = model.lng,
            lat = model.lat,
            name = model.name,
            address = model.address,
            url = model.url
        )
    }

}