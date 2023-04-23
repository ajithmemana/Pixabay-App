package com.ajithmemana.pixabay.ui.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.unit.dp
import com.ajithmemana.pixabay.R
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.ui.composable.ImageGridItem
import com.ajithmemana.pixabay.ui.theme.Dimens.card_corner_large
import com.ajithmemana.pixabay.ui.theme.Dimens.card_corner_normal
import com.ajithmemana.pixabay.ui.theme.Dimens.grid_item_padding
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_large
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_medium
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_small
import com.ajithmemana.pixabay.ui.theme.SearchBarBackground
import com.ajithmemana.pixabay.ui.theme.Typography

/**
 * Created by ajithmemana
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PixabayImagesListScreen(
    imageData: List<PixabayImageItem>, onSearchClick: (String) -> Unit,
    onImageClick: (PixabayImageItem) -> Unit = {},
    showNetworkError: MutableState<Boolean>,
) {
    Column {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(0.dp, 0.dp, card_corner_large, card_corner_large))
                .background(SearchBarBackground)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = Typography.titleLarge,
                modifier = Modifier.padding(margin_large, margin_medium, margin_large, 0.dp)
            )
            SearchBar(onSearchClick = onSearchClick)
        }
        Spacer(
            modifier = Modifier
                .height(margin_small)
                .fillMaxWidth()
        )
        AnimatedVisibility(visible = true) {
            if (showNetworkError.value) NetworkErrorMessage(
            ) { showNetworkError.value = false }
        }
        val configuration = LocalConfiguration.current
        // If image date is present show the grid, else show empty message
        if (imageData.isNotEmpty()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(grid_item_padding),
                userScrollEnabled = true
            ) {
                items(imageData.size) { index ->
                    ImageGridItem(imageData[index], onImageClick)
                }
            }
        } else {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.no_results_found),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(margin_large)
            )
        }
    }
}

@Composable
fun SearchBar(
    onSearchClick: (String) -> Unit,
) {
    var queryString by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    Row(
        Modifier
            .padding(margin_large), verticalAlignment = (Alignment.CenterVertically)

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
                .height(50.dp)
        )

        Spacer(modifier = Modifier.width(margin_medium))

        IconButton(
            onClick = { onSearchClick(queryString.text) }, modifier = Modifier
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
fun NetworkErrorMessage(dismissAlert: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(margin_large))
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(margin_large))
        Text(text = stringResource(id = R.string.error_network_not_available))
        Spacer(modifier = Modifier.width(margin_large))
        Button(onClick = { dismissAlert() }) {
            Text(text = stringResource(id = R.string.button_ok))
        }
    }
}
