package com.example.packassist.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.packassist.R


/**
 * A composable that displays a confirmation dialog.
 *
 * This dialog presents the user with a message and two buttons: "Confirm" and "Dismiss".
 * The caller can specify the text to display in the dialog, as well as the callbacks to be invoked
 * when each button is clicked.
 *
 * @param text The message to display in the dialog.
 * @param onDismiss The callback to be invoked when the "Dismiss" button is clicked.
 * @param onConfirm The callback to be invoked when the "Confirm" button is clicked.
 * @param modifier The modifier to be applied to the dialog.
 */
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