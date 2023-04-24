package com.ajithmemana.pixabay.ui.composable

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ajithmemana.pixabay.R
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_medium
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_small
import com.ajithmemana.pixabay.ui.theme.Typography
import com.ajithmemana.pixabay.ui.theme.textColorPrimary

/**
 * Composable for an individual cell of image grid
 *
 * Created by ajithmemana
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageGridItem(imageItem: PixabayImageItem, onImageItemClick: (PixabayImageItem) -> (Unit)) {

    Card(
        modifier = Modifier.padding(margin_small), onClick = { onImageItemClick(imageItem) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        AsyncImage(
            modifier = Modifier.aspectRatio(imageItem.getAspectRatio()),
            model = imageItem.previewURL,
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.Center,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(margin_medium, 0.dp),
            text = stringResource(id = R.string.label_author).plus(imageItem.user),
            style = Typography.labelLarge,
            color = textColorPrimary
        )
        Text(
            modifier = Modifier.padding(margin_medium, 0.dp, margin_medium, margin_medium),
            text = stringResource(id = R.string.label_tags).plus(imageItem.tags),
            style = Typography.labelSmall,
            color = textColorPrimary
        )
    }
}

