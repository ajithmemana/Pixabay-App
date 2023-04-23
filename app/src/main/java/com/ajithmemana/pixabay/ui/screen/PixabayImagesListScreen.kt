package com.ajithmemana.pixabay.ui.screen

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajithmemana.pixabay.R
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.ui.composable.ImageGridItem
import com.ajithmemana.pixabay.ui.theme.SearchBarBackground

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
                .clip(RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp))
                .background(SearchBarBackground)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White,
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 0.dp)
            )
            SearchBar(onSearchClick = onSearchClick, onImageClick = onImageClick)
        }
        Spacer(
            modifier = Modifier
                .height(5.dp)
                .fillMaxWidth()
        )
        AnimatedVisibility(visible = true) {
            if (showNetworkError.value) NetworkErrorMessage(
            ) { showNetworkError.value = false }
        }

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(5.dp),
            userScrollEnabled = true
        ) {
            items(imageData.size) { index ->
                ImageGridItem(imageData[index], onImageClick)
            }
        }
    }
}

@Composable
fun SearchBar(
    onSearchClick: (String) -> Unit,
    onImageClick: (PixabayImageItem) -> Unit = {},
) {
    var queryString by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        Modifier
            .padding(10.dp), verticalAlignment = (Alignment.CenterVertically)

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
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
                .height(50.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        IconButton(
            onClick = { onSearchClick(queryString.text) }, modifier = Modifier
                .size(40.dp)
                .padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_image_search),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun NetworkErrorMessage(dismissAlert: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(16.dp))
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = stringResource(id = R.string.error_network_not_available))
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = { dismissAlert() }) {
            Text(text = stringResource(id = R.string.button_ok))
        }
    }
}
