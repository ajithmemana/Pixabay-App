package com.ajithmemana.pixabay

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.startObservingDb()
        setContent {

            val images = viewModel.searchResults.collectAsState(initial = null)
            PixabayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    images.value?.let {
                        PixabayApp(imageData = it, onSearchClicked = {})
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        imagesRepository.getImagesForQueryString("fruits")
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