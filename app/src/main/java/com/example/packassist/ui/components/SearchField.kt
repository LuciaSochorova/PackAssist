package com.example.packassist.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.packassist.R

/**
 * A composable that displays a search field.
 *
 * This composable allows the user to enter a search query
 * and provides a callback function to handle the search action.
 *
 * @param onSearch he callback function to be invoked when the user performs a search.
 * @param modifier the modifier to be applied to this composable.
 * @param labelText The label text to display above the search field.
 * @param keyboardOptions The keyboard options to configure the keyboard behavior.
 * @param keyboardActions The keyboard actions to configure the actions that can be performed using the keyboard.
 * @param shape The shape of the search field.
 */
@Composable
fun SearchField(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String? = null ,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    shape: Shape = MaterialTheme.shapes.large,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    TextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onSearch(it)
        },
        label = { Text(labelText ?: stringResource(id = R.string.search))},
        shape = shape,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null)},
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier
    )
}