package com.example.packassist.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.packassist.R

@Composable
fun ConfirmationDialog(
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
                 TextButton(onClick = onConfirm) {
                     Text(text = stringResource(id = R.string.confirm_button_description))
                 }
        },
        dismissButton = { TextButton(onClick = onDismiss) {
            Text(text = stringResource(id = R.string.dismiss))
        }  },
        text = { Text(text = text) },
        modifier = modifier
    )
}