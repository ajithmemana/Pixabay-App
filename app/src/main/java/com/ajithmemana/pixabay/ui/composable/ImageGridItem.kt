package com.ajithmemana.pixabay.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem

/**
 * Created by ajithmemana
 */
@Composable
fun ImageGridItem(imageItem: PixabayImageItem) {

    Card(modifier = Modifier.padding(5.dp)) {
        AsyncImage(model = imageItem.previewURL, contentDescription = null)
        Text(
            modifier = Modifier.padding(5.dp, 0.dp),
            text = "Author: ${imageItem.user}",
            color = Color.Black
        )
        Text(
            modifier = Modifier.padding(5.dp, 0.dp),
            text = "tags: ${imageItem.tags}",
            color = Color.DarkGray,
        )
    }
}

