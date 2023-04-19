package com.ajithmemana.pixabay.viewmodel

import androidx.lifecycle.ViewModel
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.data.repository.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by ajithmemana
 */
@HiltViewModel
class ImagesViewModel @Inject constructor(private val imagesRepository: ImagesRepository) :
    ViewModel() {

    lateinit var searchResults: Flow<List<PixabayImageItem>>

    fun startObservingDb() {
        searchResults = imagesRepository.observeStoredImages()
    }

    fun performSearchUsingInput(inputString: String) {
        imagesRepository.getImagesForQueryString(inputString)
    }
}

