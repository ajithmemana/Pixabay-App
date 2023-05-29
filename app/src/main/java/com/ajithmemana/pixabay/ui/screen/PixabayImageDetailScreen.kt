package com.ajithmemana.pixabay.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ajithmemana.pixabay.R
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.ajithmemana.pixabay.ui.composable.ImageStatsItem
import com.ajithmemana.pixabay.ui.composable.TagItem
import com.ajithmemana.pixabay.ui.preview.PixabayImageItemPreviewParam
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_large
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_medium
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_small
import com.ajithmemana.pixabay.ui.theme.textColorPrimary

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
fun PixabayImageDetailScreen(
    imageItem: PixabayImageItem,
    onBackClick: () -> Unit,
    onTagClick: (String) -> Unit,
) {

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        IconButton(
            onClick = onBackClick, modifier = Modifier.padding(margin_small)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                tint = textColorPrimary,
                contentDescription = stringResource(id = R.string.content_desc_back)
            )
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageItem.getAspectRatio()),
            model = imageItem.largeImageURL,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.placeholder)
        )

        Text(
            text = stringResource(id = R.string.label_author).plus(imageItem.user),
            modifier = Modifier.padding(margin_large, margin_medium),
            color = textColorPrimary
        )

        imageItem.tags?.split(",")?.let {
            TagItem(tags = it) { tagString ->
                onTagClick(tagString)
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(0.dp, margin_medium),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ImageStatsItem(R.drawable.ic_like, imageItem.likes)
            ImageStatsItem(R.drawable.ic_comment, imageItem.comments)
            ImageStatsItem(R.drawable.ic_download, imageItem.downloads)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewImageDetailScreen(@PreviewParameter(PixabayImageItemPreviewParam::class) item: List<PixabayImageItem>) {
    PixabayImageDetailScreen(item[0], {}, {})
}