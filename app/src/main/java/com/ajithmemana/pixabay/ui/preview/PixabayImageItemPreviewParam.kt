package com.ajithmemana.pixabay.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem

/**
 * Created by ajithmemana
 */
class PixabayImageItemPreviewParam : PreviewParameterProvider<List<PixabayImageItem>> {
    private val item1 = PixabayImageItem(
        id = 1,
        user = "Ben",
        webFormatHeight = 400f,
        webFormatWidth = 600f,
        previewURL = "https://fastly.picsum.photos/id/916/200/200.jpg?hmac=hEUrLG-ayFdIoyHKUwazT8SMEsVxWH9xGz4tx-e0cN0",
        largeImageURL = "https://picsum.photos/640/480",
        tags = "Engineer, Software, Developer",
        likes = 1,
        comments = 2,
        downloads = 3,
    )
    private val item2 = PixabayImageItem(
        id = 2,
        user = "Ajith",
        webFormatHeight = 800f,
        webFormatWidth = 1200f,
        previewURL = "https://fastly.picsum.photos/id/916/200/200.jpg?hmac=hEUrLG-ayFdIoyHKUwazT8SMEsVxWH9xGz4tx-e0cN0",
        largeImageURL = "https://picsum.photos/640/480",
        tags = "Software, Developer, Programmer",
        likes = 100,
        comments = 3000,
        downloads = 40000,
    )
    override val values: Sequence<List<PixabayImageItem>>
        get() = sequenceOf(listOf(item1, item2))
}

