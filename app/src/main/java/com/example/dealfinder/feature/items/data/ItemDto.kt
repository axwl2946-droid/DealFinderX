package com.example.dealfinder.feature.items.data

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: String, // Assuming API returns ID as String
    val title: String,
    val imageUrl: String, // API should return URLs as Strings
    val oldPrice: Double,
    val newPrice: Double,
    val storeName: String,
    val storeLogoUrl: String, // API should return URLs as Strings
    val temperature: Int,
    val commentsCount: Int
)