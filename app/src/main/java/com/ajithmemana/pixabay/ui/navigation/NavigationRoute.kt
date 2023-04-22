package com.ajithmemana.pixabay.ui.navigation

/**
 * Navigation route class defines each of the screens present in the app that can be navigated to
 *
 * @property route Unique string identifier for each route
 * @constructor Create empty Navigation route
 */
enum class NavigationRoute(
    val route: String,
) {
    IMAGES_LIST("image_list"),
    IMAGE_DETAILS("image_detail")
}

/**
 * Navigation route params defined the key for parameters passed from each screen to another
 *
 * @property value
 * @constructor Create empty Navigation route params
 */
enum class NavigationRouteParams(
    val value: String,
) {
    IMAGE_ITEM("image_data"),
}
