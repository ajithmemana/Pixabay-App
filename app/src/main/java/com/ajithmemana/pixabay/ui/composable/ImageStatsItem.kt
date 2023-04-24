package com.ajithmemana.pixabay.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.ajithmemana.pixabay.ui.theme.textColorPrimary

/**
 * Composable item to display a number and icon that represent stats - ex: downloads, likes, comments etc
 *
 * @param iconRes - icon resource id
 * @param count - number associated with stats item (ex: Number of downloads)
 */
@Composable
fun ImageStatsItem(iconRes: Int, count: Int?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painterResource(id = iconRes), contentDescription = null)
        Text(
            text = "$count", fontSize = 12.sp, color = textColorPrimary
        )
    }
}