package com.kt.startkit.data.model

data class ItemModel(
    val id: String,
    val name: String,
    val price: Int,
    val count: Int,
    val category: String,
)

//data class UserResponse(
//    @Json(name = "_id")
//    val id: String,
//    @Json(name = "email")
//    val email: String,
//    @Json(name = "first_name")
//    val firstName: String,
//    @Json(name = "last_name")
//    val lastName: String,
//    @Json(name = "avatar")
//    val avatar: String
//)