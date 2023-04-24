package com.ajithmemana.pixabay

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.getSystemService
import com.ajithmemana.pixabay.data.repository.ImagesRepository
import com.ajithmemana.pixabay.ui.PixabayApp
import com.ajithmemana.pixabay.ui.theme.PixabayTheme
import com.ajithmemana.pixabay.util.isNetworkConnected
import com.ajithmemana.pixabay.util.networkCallback
import com.ajithmemana.pixabay.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imagesRepository: ImagesRepository

    private val viewModel: MainViewModel by viewModels()

    private lateinit var connectivityManager: ConnectivityManager

    private var showNetworkError = mutableStateOf(false)
    private var showEmptyQueryError = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.startObservingDb()
        observeConnectivityChanges()
        setContent {
            val images = viewModel.imageData.collectAsState(initial = null)
            PixabayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    images.value?.let {
                        PixabayApp(
                            imageData = it,
                            onSearchClicked = { queryText -> onSearchButtonClicked(queryText) },
                            showNetworkError,
                            showEmptyQueryError,
                            viewModel.showLoadingIndicator
                        )
                    }

                }

            }
        }
    }

    private fun onSearchButtonClicked(queryText: String) {

        when {
            queryText.isEmpty() -> showEmptyQueryError.value = true
            !isNetworkConnected.value -> showNetworkError.value = true
            else -> {
                showEmptyQueryError.value = false
                showNetworkError.value = false
                viewModel.fetchImagesForQueryString(queryText)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchImagesForQueryString("fruits")
    }

    override fun onStop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        super.onStop()
    }

    /**
     * Monitor internet connectivity changes and update a global state variable
     *
     */
    private fun observeConnectivityChanges() {
        connectivityManager = getSystemService(
            applicationContext,
            ConnectivityManager::class.java
        ) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PixabayTheme {
        Text(text = "Pixabay App")
    }
}
