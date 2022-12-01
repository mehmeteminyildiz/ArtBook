package com.yildiz.artbook.api

import com.yildiz.artbook.model.ImageResponse
import com.yildiz.artbook.util.Constants
import com.yildiz.artbook.util.Constants.GET_API_END_POINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET(GET_API_END_POINT)
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = Constants.API_KEY
    ): Response<ImageResponse>
}