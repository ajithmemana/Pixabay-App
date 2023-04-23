package com.ajithmemana.pixabay.data.repository

import android.util.Log
import com.ajithmemana.pixabay.data.api.PixabayImageService
import com.ajithmemana.pixabay.data.database.dao.PixabayImagesDao
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.data.models.SearchResponse
import com.ajithmemana.pixabay.util.PIXABAY_API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Repository to class to manage all image related operations
 * Created by ajithmemana
 */
class ImagesRepository @Inject constructor(
    private val pixabayImageService: PixabayImageService,
    private val pixabayImagesDao: PixabayImagesDao,
) {
    /**
     * Fetch images from Pixabay cloud for a particular query string
     *
     * @param queryString - Input string param for Pixabay API.
     */
    fun fetchImagesForQueryString(queryString: String) =
        pixabayImageService.getImagesByQueryString(PIXABAY_API_KEY, queryString).enqueue(
            object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>,
                ) {
                    // Store date to DB
                    val mappedList = response.body()?.hits?.map {
                        PixabayImageItem(
                            it?.id!!,
                            it.previewURL,
                            it.largeImageURL,
                            it.tags,
                            it.user ?: "",
                            it.likes,
                            it.comments,
                            it.downloads,
                            it.webformatWidth,
                            it.webformatHeight
                        )
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        pixabayImagesDao.deleteAll()
                        pixabayImagesDao.insertAll(mappedList)
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.d("TEST", "onFailure calling api")
                    //todo handle error
                }
            }
        )

    fun observeStoredImages() = pixabayImagesDao.getAllImages()

}

