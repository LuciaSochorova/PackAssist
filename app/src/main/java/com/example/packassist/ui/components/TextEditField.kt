package com.example.packassist.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

/**
 * Component that use OutlinedTextField with leading edit icon.
 *
 * @param value The current value of the text field.
 * @param onValueChange Called when the value of the text field changes.
 * @param keyboardAction The callback function to be invoked when the user performs a keyboard action, such as pressing the "Done" button.
 * @param modifier Modifier to be applied to the text field.
 * @param colors The colors used for the text field.
 * @param label The label to be displayed in the text field.
 * @param isError Whether the text field is in an error state.
 * @param textStyle The text style to be applied to the text field.
 */
@Composable
fun TextEditField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardAction: (KeyboardActionScope) -> Unit,
    modifier: Modifier = Modifier,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors().copy(
        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
    ),
    label: String = "",
    isError : Boolean = false,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyMedium
) {
    OutlinedTextField(
        value = value,
        label = { Text(text = label) },
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        textStyle = textStyle,
        isError = isError,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null
            )
        },
        colors = colors,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = keyboardAction),
        shape = RoundedCornerShape(0.dp)
    )

}
