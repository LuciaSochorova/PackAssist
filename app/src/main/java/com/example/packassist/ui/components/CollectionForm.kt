package com.example.packassist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.data.entitiesAndDaos.ItemsOfCollection

@Composable
fun CollectionField(
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
                for (item in collection.items) {
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

        }

    }
}


data class CollectionFormInformation(
    val name: String = "",
    val newItem: String = "",
    val items: List<String> = listOf()
)

@Preview(showBackground = true)
@Composable
fun CollectionFieldPreview() {

}