package com.ajithmemana.pixabay.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ajithmemana.pixabay.R
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_medium
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_small

@Composable
fun ConfirmationDialog(dismissAlert: () -> Unit, proceedToDetails: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            dismissAlert()
        },
        title = {
            Text(text = stringResource(R.string.title_confirmation_dialog))
        },
        text = {
            Text(
                stringResource(R.string.message_confirmation_dialog)
            )
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = margin_medium),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .weight(1.0f)
                        .padding(margin_small),
                    onClick = { dismissAlert() }
                ) {
                    Text(stringResource(R.string.button_cancel))
                }
                Button(
                    modifier = Modifier
                        .weight(1.0f)
                        .padding(margin_small),
                    onClick = { proceedToDetails() }
                ) {
                    Text(stringResource(R.string.button_proceed))
                }
            }
        }
    )
}