package com.kt.startkit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 즐겨찾기 추가한 마커
 */
@Entity
data class FavoriteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val lng: Double,

    val lat: Double,

    val name: String,

    val address: String,

    val url: String,
)
