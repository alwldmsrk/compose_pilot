package com.kt.startkit.domain.mapper

import com.kt.startkit.data.model.Document
import com.kt.startkit.data.model.ItemModel
import com.kt.startkit.data.model.PlaceResponse
import com.kt.startkit.domain.entity.Item
import com.kt.startkit.domain.entity.ItemCategory
import com.kt.startkit.domain.entity.PlaceData
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class PlaceDomainMapper @Inject constructor() : Mapper<Document, PlaceData> {
    override fun invoke(model: Document): PlaceData {
        return PlaceData(
            id = model.id,    //장소 ID
            place_name = model.place_name,    //장소명, 업체명
            category_name = model.category_name, //카테고리 이름
            category_group_code = model.category_group_code, //중요 카테고리만 그룹핑한 카테고리 그룹 코드
            category_group_name = model.category_group_name, //중요 카테고리만 그룹핑한 카테고리 그룹명
            phone = model.phone,    //전화번호
            address_name = model.address_name,    //전체 지번 주소
            road_address_name = model.road_address_name, //전체 도로명 주소
            x = model.x, // X 좌표 혹은 경도(longitude)
            y = model.y,    //Y 좌표 혹은 위도(latitude)
            place_url = model.place_url,    //장소 상세 페이지 URL
            distance = model.distance
        )
    }
}