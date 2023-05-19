package com.ajithmemana.pixabay.data.api

import com.ajithmemana.pixabay.data.models.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service class to communicate with cloud server
 * Created by ajithmemana
 */
interface PixabayImageService {
    @GET("api/")
   suspend fun getImagesByQueryString(
        @Query("key") apiKey: String,
        @Query("q") query: String,
    ): SearchResponse
}

