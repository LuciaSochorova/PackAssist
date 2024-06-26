package com.example.packassist.ui.screens.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import kotlinx.coroutines.launch


/**
 * A composable function that is used to create a new event.
 *
 * @param state The current ui state.
 * @param onDismiss The callback function to dismiss the dialog.
 * @param saveEvent The callback function to save the event.
 * @param navigateToEventDetails The callback function to navigate to the event details screen.
 * @param onNameChange The callback function to update the event name.
 * @param modifier The modifier to apply.
 */
@Composable
fun EventCreationDialog(
    state: String,
    onDismiss: () -> Unit,
    saveEvent: suspend () -> Int,
    navigateToEventDetails: (Int) -> Unit,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier.background(MaterialTheme.colorScheme.surfaceDim),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.create_new_event),
            style = MaterialTheme.typography.titleLarge
        )
        TextField(
            value = state,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            onValueChange = onNameChange,
            label = { Text(text = stringResource(R.string.event_name)) },
            isError = state.isBlank(),
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onDismiss)
            {
                Text(text = stringResource(R.string.dismiss))
            }

            TextButton(
                onClick = {
                    if (state.isNotBlank()) {
                        coroutineScope.launch {
                            val id = saveEvent()
                            navigateToEventDetails(id)
                        }
                    }
                }
            ) {
                Text(text = stringResource(R.string.create))
            }


        }
    }
}