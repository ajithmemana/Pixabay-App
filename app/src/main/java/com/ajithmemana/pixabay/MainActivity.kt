package com.ajithmemana.pixabay

import android.net.ConnectivityManager
import android.net.Network
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
import com.ajithmemana.pixabay.viewmodel.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imagesRepository: ImagesRepository
    private val viewModel: ImagesViewModel by viewModels()
    var showNetworkError = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.startObservingDb()
        monitorConnectivity()
        setContent {
            val images = viewModel.searchResults.collectAsState(initial = null)
            PixabayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    images.value?.let {
                        PixabayApp(
                            imageData = it,
                            onSearchClicked = { query -> searchForImages(query) },
                            showNetworkError
                        )
                    }
                }
            }
        }
    }

    private fun searchForImages(queryText: String) {
        if (networkCallback.isNetworkConnected.value) {
            showNetworkError.value = false
            viewModel.performSearchUsingInput(queryText)
        } else {
            // Show network error
            showNetworkError.value = true
        }
    }

    override fun onResume() {
        super.onResume()
        imagesRepository.getImagesForQueryString("fruits")
    }

    /**
     * Monitor internet connectivity changes and update a local state variable
     *
     */
    private fun monitorConnectivity() {

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val connectivityManager = getSystemService(
            applicationContext,
            ConnectivityManager::class.java
        ) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PixabayTheme {
        Greeting("Android")
    }
}

private val networkCallback = object : ConnectivityManager.NetworkCallback() {
    var isNetworkConnected = mutableStateOf(true)

    // network is available for use
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isNetworkConnected.value = true
    }

    // Network capabilities have changed for the network
    override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities,
    ) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        val unMetered =
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        isNetworkConnected.value = unMetered
    }

    // lost network connection
    override fun onLost(network: Network) {
        super.onLost(network)
        isNetworkConnected.value = false
    }
}
