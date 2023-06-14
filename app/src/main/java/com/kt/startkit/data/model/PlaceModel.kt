package com.kt.startkit.data.model

data class PlaceResponse(
    val meta: Meta,
    val documents: List<Document>
)

data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val same_name: SameName?,
    val total_count: Int

)

data class SameName(
    val region: List<String>?,    // 질의어에서 인식된 지역의 리스트 (예: '중앙로 맛집' 에서 '중앙로'에 해당하는 지역 리스트)
    val keyword: String?, // 질의어에서 지역 정보를 제외한 키워드 (예: '중앙로 맛집' 에서 '맛집')
    val selected_region: String?,    //인식된 지역 리스트 중 현재 검색에 사용된 지역 정보
)

data class Document(
    val id: String,  // 장소 ID
    val place_name: String,  // 장소명, 업체명
    val category_name: String,  // 카테고리 이름
    val category_group_code: String, // 중요 카테고리만 그룹핑한 카테고리 그룹 코드
    val category_group_name: String, // 중요 카테고리만 그룹핑한 카테고리 그룹명
    val phone: String, // 전화번호
    val address_name: String, // 전체 지번 주소
    val road_address_name: String, // 전체 도로명 주소
    val x: String, // X 좌표 혹은 경도(longitude)
    val y: String, // Y 좌표 혹은 위도(latitude)
    val place_url: String, // 장소 상세 페이지 URL
    val distance: String,
)
