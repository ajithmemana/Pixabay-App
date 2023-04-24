package com.ajithmemana.pixabay.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_large
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_medium
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_small

/**
 * Custom tag chip with click
 *
 * @param tags - List of tag strings
 * @param onItemClick - Callback to calling composable
 */
@Composable
fun TagItem(tags: List<String>, onItemClick: (tagString: String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(margin_large, 0.dp)
    ) {
        tags.forEach() {
            Text(
                text = it,
                color = Color.White,
                modifier = Modifier
                    .background(
                        Color.Black, shape = RoundedCornerShape(8.dp)
                    )
                    .padding(margin_small)
                    .height(24.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { onItemClick(it) },
            )
            Spacer(modifier = Modifier.width(margin_medium))
        }
    }
}

