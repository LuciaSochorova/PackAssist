package com.example.packassist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.data.entitiesAndDaos.ItemsOfCollection


/**
 * A composable that displays a card representing a collection.
 * This composable displays the collection's name, a list of its items, and an edit button.
 *
 * @param collection The collection to display.
 * @param editAction The callback to be invoked when the edit button is clicked.
 * @param modifier The modifier to be applied to this composable.
 */
@Composable
fun CollectionCard(
    collection: ItemsOfCollection,
    editAction: (collectionId: Int) -> Unit,
    modifier: Modifier = Modifier
) {


    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier
            .border(
                color = MaterialTheme.colorScheme.outline,
                width = 1.dp,
                shape = MaterialTheme.shapes.extraSmall
            )
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = collection.collection.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                IconButton(
                    onClick = { editAction(collection.collection.id) },
                    modifier = Modifier

                ) {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = stringResource(R.string.edit_button_description),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.fillMaxSize(0.8f)
                    )
                }
            }
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)) {
                for (item in collection.items.sortedBy { it.name }) {
                    HorizontalDivider()
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
                    )
                }
            }

        }
    }
}


/**
 * A composable that displays a form for creating or editing a collection.
 *
 * @param modifier The modifier to be applied to this composable.
 * @param information he information about the collection to display.
 * @param onNameChange The callback to be invoked when the name of the collection changes.
 * @param onNewItemChange The callback to be invoked when the new item of the collection changes.
 * @param onAddItem The callback to be invoked when the add item button is clicked.
 * @param onChangeItem The callback to be invoked when an item name of the collection changes.
 * @param onEditItem The callback to be invoked when an item is edited.
 */
@Composable
fun CollectionForm(
    modifier: Modifier = Modifier,
    information: CollectionFormInformation,
    onNameChange: (String) -> Unit,
    onNewItemChange: (String) -> Unit,
    onAddItem: () -> Unit,
    onChangeItem: (String, Int) -> Unit,
    onEditItem: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
    ) {
        TextEditField(
            value = information.name,
            onValueChange = onNameChange,
            isError = information.name.isBlank(),
            keyboardAction = { focusManager.clearFocus() },
            label = stringResource(R.string.type_name_label),
            textStyle = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        TextEditField(
            value = information.newItem,
            onValueChange = onNewItemChange,
            keyboardAction = {
                focusManager.clearFocus()
                onAddItem()
            },
            label = stringResource(R.string.type_new_item_label),
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

        LazyColumn {
            itemsIndexed(information.items) { index, item ->
                TextEditField(
                    value = item,
                    onValueChange = { onChangeItem(it, index) },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    keyboardAction = {
                        onEditItem()
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.onFocusChanged { onEditItem() }
                )
            }
            
            item { Spacer(modifier = Modifier.size(36.dp)) }

        }

    }
}


/**
 * Data class representing the information about a collection to be displayed in a form.
 *
 * @property name The name of the collection.
 * @property newItem The name of the new item to be added.
 * @property items The list of items in the collection.
 * @constructor Create empty Collection form information
 */
data class CollectionFormInformation(
    val name: String = "",
    val newItem: String = "",
    val items: List<String> = listOf()
)
