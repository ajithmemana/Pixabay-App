package com.ajithmemana.pixabay.ui

/**
 * Created by ajithmemana
 */
class MainUiEvent(
    val showEmptyQueryError: (Boolean) -> Unit = {},
    val showLoadingIndicator: (Boolean) -> Unit = {},
    val showNetworkError: (Boolean) -> Unit = {},
)
