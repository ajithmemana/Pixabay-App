package com.ajithmemana.pixabay.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem

/**
 * Created by ajithmemana
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageGridItem(imageItem: PixabayImageItem, onImageItemClick: (PixabayImageItem) -> (Unit)) {

    Card(modifier = Modifier.padding(5.dp), onClick = { onImageItemClick(imageItem) }) {
        AsyncImage(model = imageItem.previewURL, contentDescription = null)
        Text(
            modifier = Modifier.padding(8.dp, 0.dp),
            text = "Author: ${imageItem.user}",
            fontSize = 14.sp,
            color = Color.Black,
            lineHeight = 18.sp
        )
        Text(
            modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
            text = "tags: ${imageItem.tags}",
            color = Color.DarkGray,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    }
}

