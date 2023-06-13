package com.kt.startkit.data

import com.kt.startkit.data.model.PlaceResponse
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.*

interface ApiService {

    @Headers("Authorization: $API_AUTHORIZATION_PREFIX $API_ACCESS_KEY")
    @GET("v2/local/search/keyword.JSON")
    suspend fun getPlaces(
        @Query("query") query: String,
        @Query("rect") rect: String,
    ) : PlaceResponse

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create<ApiService>()
    }
}
private const val API_AUTHORIZATION_PREFIX = "KakaoAK"
private const val API_ACCESS_KEY = "f3d2a2d15e3b8ac59748b35e5f38136a"
private const val DEFAULT_SORT = "accuracy"
private const val DEFAULT_PAGE = 1
private const val NETWORK_DEFAULT_SIZE = 30
