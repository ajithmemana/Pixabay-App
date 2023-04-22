package com.ajithmemana.pixabay.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ajithmemana.pixabay.R
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem

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

    Column {
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                //todo add 'back' string
                contentDescription = stringResource(id = R.string.app_name)
            )
        }
        Text(text = "Detail page for image with id ${imageItem.user} ${imageItem.id}")
    }
}