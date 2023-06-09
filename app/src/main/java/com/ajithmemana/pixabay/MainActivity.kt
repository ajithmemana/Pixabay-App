package com.ajithmemana.pixabay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ajithmemana.pixabay.data.repository.ImagesRepository
import com.ajithmemana.pixabay.ui.PixabayApp
import com.ajithmemana.pixabay.ui.MainUiEvent
import com.ajithmemana.pixabay.ui.theme.PixabayTheme
import com.ajithmemana.pixabay.util.ConnectivityObserver
import com.ajithmemana.pixabay.util.NetworkConnectivityObserver
import com.ajithmemana.pixabay.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imagesRepository: ImagesRepository

    private val viewModel: MainViewModel by viewModels()

    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        viewModel.startObservingDb()
        setContent {
            val images = viewModel.imageData.collectAsState(initial = null)
            val mainUiState by viewModel.mainUiState.collectAsState()
            val mainUiEvent = MainUiEvent(
                showEmptyQueryError = { showError -> viewModel.showEmptyQueryError(showError) },
                showLoadingIndicator = { showIndicator ->
                    viewModel.showLoadingIndicator(
                        showIndicator
                    )
                },
                showNetworkError = { showError -> viewModel.showNetworkError(showError) }
            )
            PixabayTheme {
                val connectionStatus by connectivityObserver.observe()
                    .collectAsState(initial = ConnectivityObserver.Status.UNAVAILABLE)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    images.value?.let {
                        PixabayApp(
                            imageData = it,
                            onSearchClicked = { queryText ->
                                onSearchButtonClicked(
                                    queryText,
                                    connectionStatus
                                )
                            },
                            connectionStatus,
                            mainUiState = mainUiState,
                            mainUiEvent = mainUiEvent
                        )
                    }

                }

            }
        }
    }

    private fun onSearchButtonClicked(
        queryText: String,
        connectionStatus: ConnectivityObserver.Status,
    ) {

        when {
            queryText.isEmpty() -> viewModel.showEmptyQueryError(true)
            connectionStatus != ConnectivityObserver.Status.AVAILABLE -> viewModel.showNetworkError(
                true
            )

            else -> {
                viewModel.showEmptyQueryError(false)
                viewModel.showNetworkError(false)
                viewModel.fetchImagesForQueryString(queryText)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //  viewModel.fetchImagesForQueryString(DEFAULT_QUERY_TEXT)
    }

    override fun onStop() {
        super.onStop()
    }
}
