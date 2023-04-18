package com.ajithmemana.pixabay.data.repository

import android.util.Log
import com.ajithmemana.pixabay.data.api.PixabayImageService
import com.ajithmemana.pixabay.data.models.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by ajithmemana
 */
class ImagesRepository @Inject constructor(private val pixabayImageService: PixabayImageService) {

    fun getImagesForQueryString() =
        pixabayImageService.getImagesByQueryString(PIXABAY_API_KEY, "Bird").enqueue(
            object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>,
                ) {
                    Log.d("TEST", "Success calling api")
                    Log.d("TEST", "${response.body()}")
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.d("TEST", "onFailure calling api")
                }
            }
        )

    companion object {
        const val PIXABAY_API_KEY = "18991208-205c1e43c5088662de2c7c8ff"
    }
}

