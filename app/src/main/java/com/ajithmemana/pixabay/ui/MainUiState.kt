package com.ajithmemana.pixabay.ui

data class MainUiState(
    val showLoadingIndicator: Boolean = false,
    val showNetworkError: Boolean = false,
    val showEmptyQueryError: Boolean = false,
)