package com.ajithmemana.pixabay.data.repository

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
) : ImagesRepositoryInterface {
    /**
     * Fetch images from Pixabay cloud for a particular query string
     *
     * @param queryString - Input string param for Pixabay API.
     */
    override suspend fun fetchImagesForQueryString(
        queryString: String,
        onSearchComplete: () -> Unit,
    ) {
        val response = pixabayImageService.getImagesByQueryString(PIXABAY_API_KEY, queryString)

        val mappedList = response?.hits?.map {
            PixabayImageItem(
                it?.id!!,
                it.previewURL,
                it.largeImageURL,
                it.tags,
                it.user ?: "",
                it.likes,
                it.comments,
                it.downloads,
                it.webFormatWidth,
                it.webFormatHeight
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            pixabayImagesDao.deleteAll()
            pixabayImagesDao.insertAll(mappedList)
        }
        onSearchComplete()
    }

    override fun observeStoredImages() = pixabayImagesDao.getAllImages()
}

