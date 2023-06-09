package com.ajithmemana.pixabay.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.ui.navigation.NavigationRoute
import com.ajithmemana.pixabay.ui.navigation.NavigationRouteParams
import com.ajithmemana.pixabay.ui.navigation.PixabayImageItemNavType
import com.ajithmemana.pixabay.ui.screen.PixabayImageDetailScreen
import com.ajithmemana.pixabay.ui.screen.PixabayImagesListScreen
import com.ajithmemana.pixabay.util.ConnectivityObserver
import com.squareup.moshi.Moshi

/**
 * Pixabay application main UI entry point
 *
 * Created by ajithmemana
 */
@Composable
fun PixabayApp(
    imageData: List<PixabayImageItem>,
    onSearchClicked: (String) -> Unit = {},
    connectionStatus: ConnectivityObserver.Status,
    mainUiState: MainUiState,
    mainUiEvent: MainUiEvent,
) {
    val navController = rememberNavController()
    PixabayAppNavHost(
        navController = navController,
        imageData = imageData,
        onSearchClicked = onSearchClicked,
        connectionStatus = connectionStatus,
        mainUiState = mainUiState,
        mainUiEvent = mainUiEvent,
    )
}

@Composable
fun PixabayAppNavHost(
    navController: NavHostController,
    imageData: List<PixabayImageItem>,
    onSearchClicked: (String) -> Unit = {},
    connectionStatus: ConnectivityObserver.Status,
    mainUiState: MainUiState,
    mainUiEvent: MainUiEvent,
) {
    NavHost(
        navController = navController, startDestination = NavigationRoute.IMAGES_LIST.route
    ) {
        // Screen 1 - List
        composable(NavigationRoute.IMAGES_LIST.route) {
            PixabayImagesListScreen(
                imageData,
                onSearchClick = { onSearchClicked(it) },
                onImageClick = {
                    val moshi = Moshi.Builder().build().adapter(PixabayImageItem::class.java)
                    val data = Uri.encode(moshi.toJson(it))
                    navController.navigate("${NavigationRoute.IMAGE_DETAILS.route}/$data")
                },
                connectionStatus,
                mainUiState,
                mainUiEvent
            )
        }
        // Screen 2 - Details
        composable(
            "${NavigationRoute.IMAGE_DETAILS.route}/{${NavigationRouteParams.IMAGE_ITEM.value}}",
            arguments = listOf(navArgument(NavigationRouteParams.IMAGE_ITEM.value) {
                type = PixabayImageItemNavType()
            })
        ) {
            val data =
                it.arguments?.getParcelable<PixabayImageItem>(NavigationRouteParams.IMAGE_ITEM.value)
            data?.let { imageItem ->
                PixabayImageDetailScreen(
                    imageItem,
                    onBackClick = { navController.navigateUp() },
                    onTagClick = {
                        //TODO Optional - Initiate a search with tag string and go to home screen
                    })
            }
        }

    }
}