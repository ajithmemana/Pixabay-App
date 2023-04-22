package com.ajithmemana.pixabay.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ajithmemana.pixabay.R
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.ui.composable.ImageStatsItem

/**
 * Pixabay image detail screen
 *
 * @param imageItem Image item data to be displayed in the screen
 * @param onBackClick Callback for back button press from the screen
 * @receiver
 *
 * Created by ajithmemana
 */
@Composable
fun PixabayImageDetailScreen(imageItem: PixabayImageItem, onBackClick: () -> Unit) {

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                //todo add 'back' string
                contentDescription = stringResource(id = R.string.app_name)
            )
        }
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = imageItem.largeImageURL,
            contentDescription = null
        )
        Text(text = "Author: ${imageItem.user}", modifier = Modifier.padding(10.dp, 5.dp))
        Text(text = "tags: ${imageItem.tags}", modifier = Modifier.padding(10.dp, 5.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp),

            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ImageStatsItem(R.drawable.ic_like, imageItem.likes)
            ImageStatsItem(R.drawable.ic_comment, imageItem.comments)
            ImageStatsItem(R.drawable.ic_download, imageItem.downloads)

        }
    }
}