package com.ajithmemana.pixabay.data.repository

import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import kotlinx.coroutines.flow.Flow

interface ImagesRepositoryInterface {

    suspend fun fetchImagesForQueryString(queryString: String, onSearchComplete: () -> Unit)
    fun observeStoredImages(): Flow<List<PixabayImageItem>>
}