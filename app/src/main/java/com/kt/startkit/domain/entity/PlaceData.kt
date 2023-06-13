package com.kt.startkit.domain.entity

/**
 * 키워드 검색 응답으로 받은 장소정보
 */
data class PlaceData(
    val id: String,    //장소 ID
    val place_name: String,    //장소명, 업체명
    val category_name: String,    //카테고리 이름
    val category_group_code: String, //중요 카테고리만 그룹핑한 카테고리 그룹 코드
    val category_group_name: String, //중요 카테고리만 그룹핑한 카테고리 그룹명
    val phone: String,    //전화번호
    val address_name: String,    //전체 지번 주소
    val road_address_name: String, //전체 도로명 주소
    val x: String,    //X 좌표 혹은 경도(longitude)
    val y: String,    //Y 좌표 혹은 위도(latitude)
    val place_url: String,    //장소 상세 페이지 URL
    val distance: String,
)
