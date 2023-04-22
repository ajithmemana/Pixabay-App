package com.ajithmemana.pixabay.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.ajithmemana.pixabay.R
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.ui.composable.ImageGridItem
import com.ajithmemana.pixabay.viewmodel.ImagesViewModel

/**
 * Created by ajithmemana
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PixabayImagesListScreen(
    imageData: List<PixabayImageItem>, onSearchClick: (String) -> Unit,
    onImageClick: (PixabayImageItem) -> Unit = {},
) {

    Column {
        var queryString by remember { mutableStateOf(TextFieldValue("")) }
        Row(
            Modifier
                .padding(10.dp), verticalAlignment = (Alignment.CenterVertically)

        ) {

            TextField(
                value = queryString,
                onValueChange = {
                    queryString = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearchClick(queryString.text)
                }),
                modifier = Modifier.weight(1.0f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_image_search),
                    contentDescription = null,
                )
            }
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

