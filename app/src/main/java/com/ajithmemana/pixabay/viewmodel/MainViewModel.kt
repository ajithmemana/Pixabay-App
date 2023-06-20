package com.ajithmemana.pixabay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.data.repository.ImagesRepository
import com.ajithmemana.pixabay.ui.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ajithmemana
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val imagesRepository: ImagesRepository) :
    ViewModel() {

    lateinit var imageData: Flow<List<PixabayImageItem>>

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

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
        showLoadingIndicator(true)
        viewModelScope.launch {
            imagesRepository.fetchImagesForQueryString(
                inputString
            ) { showLoadingIndicator(false) }
        }
    }

    // State setters
    fun showEmptyQueryError(showError: Boolean) {
        _mainUiState.update {
            it.copy(showEmptyQueryError = showError)
        }
    }

    fun showLoadingIndicator(showIndicator: Boolean) {
        _mainUiState.update {
            it.copy(showLoadingIndicator = showIndicator)
        }
    }

    fun showNetworkError(showError: Boolean) {
        _mainUiState.update {
            it.copy(showNetworkError = showError)
        }
    }
}

