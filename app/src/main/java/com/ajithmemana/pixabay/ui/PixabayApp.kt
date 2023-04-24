package com.ajithmemana.pixabay.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.google.gson.Gson

/**
 * Pixabay application main UI entry point
 *
 * Created by ajithmemana
 */
@Composable
fun PixabayApp(
    imageData: List<PixabayImageItem>,
    onSearchClicked: (String) -> Unit = {},
    showNetworkError: MutableState<Boolean>,
    showEmptyStringError: MutableState<Boolean>,
    showLoadingIndicator: MutableState<Boolean>,
) {
    val navController = rememberNavController()
    PixabayAppNavHost(
        navController = navController,
        imageData = imageData,
        onSearchClicked = onSearchClicked,
        showNetworkError = showNetworkError,
        showEmptyStringError = showEmptyStringError,
        showLoadingIndicator = showLoadingIndicator
    )
}

@Composable
fun PixabayAppNavHost(
    navController: NavHostController,
    imageData: List<PixabayImageItem>,
    onSearchClicked: (String) -> Unit = {},
    showNetworkError: MutableState<Boolean>,
    showEmptyStringError: MutableState<Boolean>,
    showLoadingIndicator: MutableState<Boolean>,
) {
    NavHost(
        navController = navController, startDestination = NavigationRoute.IMAGES_LIST.route
    ) {
        // Screen 1
        composable(NavigationRoute.IMAGES_LIST.route) {
            PixabayImagesListScreen(
                imageData,
                onSearchClick = { onSearchClicked(it) },
                onImageClick = {
                    val data = Uri.encode(Gson().toJson(it))
                    navController.navigate("${NavigationRoute.IMAGE_DETAILS.route}/$data")
                },
                showNetworkError,
                showEmptyStringError,
                showLoadingIndicator
            )
        }
        // Screen 2
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