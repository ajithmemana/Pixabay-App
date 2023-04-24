package com.ajithmemana.pixabay.viewmodel

import androidx.compose.runtime.mutableStateOf
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
class MainViewModel @Inject constructor(private val imagesRepository: ImagesRepository) :
    ViewModel() {

    lateinit var imageData: Flow<List<PixabayImageItem>>
    var showLoadingIndicator = mutableStateOf(false)
    var showNetworkError = mutableStateOf(false)
    var showEmptyQueryError = mutableStateOf(false)
    /**
     * Method to start observing database for changes and storing them to a local variable for UI
     *
     */
    fun startObservingDb() {
        imageData = imagesRepository.observeStoredImages()
    }

    /**
     * Perform a search in Pixabay cloud server using an input string.
     * Returned data is stored directly into db in an asynchronous manner
     * A higher order function is called back from repository class to indicate completion
     *
     * @param inputString - Text to be searched for
     */
    fun fetchImagesForQueryString(inputString: String) {
        showLoadingIndicator.value = true
        imagesRepository.fetchImagesForQueryString(
            inputString
        ) { showLoadingIndicator.value = false }
    }
}

