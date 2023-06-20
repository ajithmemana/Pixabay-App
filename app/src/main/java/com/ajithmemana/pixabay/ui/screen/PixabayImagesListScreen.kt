package com.ajithmemana.pixabay.ui.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.ajithmemana.pixabay.R
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.ui.composable.ConfirmationDialog
import com.ajithmemana.pixabay.ui.composable.ImageGridItem
import com.ajithmemana.pixabay.ui.preview.PixabayImageItemPreviewParam
import com.ajithmemana.pixabay.ui.MainUiEvent
import com.ajithmemana.pixabay.ui.MainUiState
import com.ajithmemana.pixabay.ui.theme.Dimens.card_corner_large
import com.ajithmemana.pixabay.ui.theme.Dimens.card_corner_normal
import com.ajithmemana.pixabay.ui.theme.Dimens.grid_item_padding
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_large
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_medium
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_small
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_xlarge
import com.ajithmemana.pixabay.ui.theme.Typography
import com.ajithmemana.pixabay.ui.theme.searchBarBackground
import com.ajithmemana.pixabay.ui.theme.textColorPrimary
import com.ajithmemana.pixabay.util.ConnectivityObserver
import com.ajithmemana.pixabay.util.NUM_ROWS_LANDSCAPE
import com.ajithmemana.pixabay.util.NUM_ROWS_PORTRAIT

/**
 * Created by ajithmemana
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PixabayImagesListScreen(
    imageData: List<PixabayImageItem>,
    onSearchClick: (String) -> Unit,
    onImageClick: (PixabayImageItem) -> Unit = {},
    connectionStatus: ConnectivityObserver.Status,
    mainUiState: MainUiState,
    mainUiEvent: MainUiEvent,
) {
    val tappedImageItem = remember { mutableStateOf<PixabayImageItem?>(null) }
    Column {
        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        0.dp, 0.dp, card_corner_large, card_corner_large
                    )
                )
                .background(searchBarBackground)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = Typography.titleLarge,
                modifier = Modifier.padding(margin_large, margin_large, margin_large, 0.dp),
                color = Color.White
            )
            SearchBar(onSearchClick = onSearchClick)
        }
        Spacer(
            modifier = Modifier
                .height(margin_medium)
                .fillMaxWidth()
        )

        // Empty string warning
        AnimatedVisibility(visible = mainUiState.showEmptyQueryError) {
            ErrorMessage(R.string.error_empty_search_query) {
                mainUiEvent.showEmptyQueryError(false)
            }
        }
        // Network warning
        AnimatedVisibility(visible = (mainUiState.showNetworkError && connectionStatus != ConnectivityObserver.Status.AVAILABLE)) {
            ErrorMessage(R.string.error_network_not_available) {
                mainUiEvent.showNetworkError(false)
            }
        }
        // Loading indicator
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(), visible = mainUiState.showLoadingIndicator
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator(modifier = Modifier.size(32.dp))
            }
        }

        val configuration = LocalConfiguration.current
        val numOfColumns =
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) NUM_ROWS_LANDSCAPE else NUM_ROWS_PORTRAIT
        // If image date is present show grid, else show empty list message
        if (imageData.isNotEmpty()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(numOfColumns),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(grid_item_padding),
                userScrollEnabled = true
            ) {
                items(imageData.size) { index ->
                    ImageGridItem(imageData[index]) { data ->
                        tappedImageItem.value = data
                    }
                }
            }
        } else if (!mainUiState.showLoadingIndicator) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.no_results_found),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(margin_large),
                color = textColorPrimary
            )
        }
    }
    // Confirmation Dialogue - If an item is selected, show confirmation dialogue
    if (tappedImageItem.value != null) {
        ConfirmationDialog(dismissAlert = { tappedImageItem.value = null }) {
            tappedImageItem.value?.let { it1 ->
                if (connectionStatus == ConnectivityObserver.Status.AVAILABLE) {
                    onImageClick(it1)
                } else {
                    mainUiEvent.showNetworkError(false)
                    tappedImageItem.value = null
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    onSearchClick: (String) -> Unit,
) {
    var queryString by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }

    Row(
        Modifier.padding(margin_large), verticalAlignment = (Alignment.CenterVertically)

    ) {

        OutlinedTextField(
            value = queryString,
            onValueChange = {
                queryString = it
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClick(queryString.text)
            }),
            modifier = Modifier
                .weight(1.0f)
                .clip(RoundedCornerShape(card_corner_normal))
                .background(Color.White)
                .height(50.dp),
        )

        Spacer(modifier = Modifier.width(margin_medium))

        IconButton(
            onClick = { onSearchClick(queryString.text) },
            modifier = Modifier
                .size(36.dp)
                .padding(margin_small)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_image_search),
                contentDescription = stringResource(id = R.string.content_desc_search),
            )
        }
    }
}

@Composable
fun ErrorMessage(resId: Int, dismissAlert: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(margin_xlarge))
        Text(
            text = stringResource(id = resId), color = textColorPrimary
        )
        Spacer(modifier = Modifier.width(margin_large))
        Button(
            onClick = { dismissAlert() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.DarkGray, contentColor = Color.White
            ),
        ) {
            Text(text = stringResource(id = R.string.button_ok))
        }
    }
}

@Preview
@Composable
fun previewErrorMessage() {
    ErrorMessage(resId = R.string.app_name) {
        // Do nothing
    }
}

@Preview
@Composable
fun previewSearchBar() {
    SearchBar() {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun previewScreen(@PreviewParameter(PixabayImageItemPreviewParam::class) item: List<PixabayImageItem>) {
    val falseState = remember {
        mutableStateOf(false)
    }
    PixabayImagesListScreen(
        imageData = item,
        mainUiState = MainUiState(
            showLoadingIndicator = false,
            showNetworkError = false,
            showEmptyQueryError = false
        ),
        mainUiEvent = MainUiEvent(),
        onImageClick = { },
        onSearchClick = { },
        connectionStatus = ConnectivityObserver.Status.AVAILABLE
    )
}