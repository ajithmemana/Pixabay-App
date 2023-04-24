package com.ajithmemana.pixabay.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ajithmemana.pixabay.R
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_medium
import com.ajithmemana.pixabay.ui.theme.Dimens.margin_small
import com.ajithmemana.pixabay.ui.theme.buttonColor
import com.ajithmemana.pixabay.ui.theme.textColorPrimary

@Composable
fun ConfirmationDialog(dismissAlert: () -> Unit, proceedToDetails: () -> Unit) {
    AlertDialog(
        backgroundColor = MaterialTheme.colorScheme.background,
        onDismissRequest = {
            dismissAlert()
        },
        title = {
            Text(
                text = stringResource(R.string.title_confirmation_dialog),
                color = textColorPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(
                text = stringResource(R.string.message_confirmation_dialog),
                color = textColorPrimary
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
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = buttonColor,
                        contentColor = Color.White
                    ),
                    onClick = { dismissAlert() },
                ) {
                    Text(stringResource(R.string.button_cancel))
                }
                Button(
                    modifier = Modifier
                        .weight(1.0f)
                        .padding(margin_small),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = buttonColor,
                        contentColor = Color.White
                    ),
                    onClick = { proceedToDetails() },

                    ) {
                    Text(stringResource(R.string.button_proceed))
                }
            }
        }
    )
}