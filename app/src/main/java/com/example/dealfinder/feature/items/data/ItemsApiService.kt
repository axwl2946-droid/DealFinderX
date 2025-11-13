package com.example.dealfinder.feature.items.data

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.dealfinder.feature.items.domain.Item
import kotlinx.serialization.json.Json
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

interface ItemsApiService {

    // Placeholder URL: Replace with your actual API endpoint
    companion object {
        private const val BASE_URL = "https://api.example.com/"

        fun create(): ItemsApiService {
            val contentType = "application/json".toMediaType()
            val json = Json { ignoreUnknownKeys = true }

            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
            return retrofit.create(ItemsApiService::class.java)
        }
    }

    @GET("items") // Assuming your API endpoint for items is '/items'
    suspend fun fetchItems(@Query("category") category: String? = null): List<ItemDto>
}
