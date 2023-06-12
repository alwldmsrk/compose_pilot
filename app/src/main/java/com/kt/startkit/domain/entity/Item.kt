package com.kt.startkit.domain.entity

enum class ItemCategory {
    Fruits,
    Bread,
    Etc;

    companion object {
        fun fromValue(value: String): ItemCategory = when (value) {
            "fruits" -> Fruits
            "bread" -> Bread
            "etc" -> Etc
            else -> Etc
        }
    }
}

data class Item(
    val id: String,
    val name: String,
    val price: Int,
    val count: Int,
    val category: ItemCategory
)

