package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.ui.components.CollectionForm
import com.example.packassist.ui.components.CollectionFormInformation
import com.example.packassist.ui.components.ThreeIconButtonsTopBar
import kotlinx.coroutines.launch

@Composable
fun CollectionCreationScreen(
    collectionUiState: CollectionCreationUiState,
    onNameChange: (String) -> Unit,
    onNewItemChange: (String) -> Unit,
    onChangeItem: (String, Int) -> Unit,
    onEditItem: () -> Unit,
    saveCollection: () -> Unit,
    onAddItem: () -> Unit,
    navigateBack: () -> Unit,
    onShowImport: (Boolean) -> Unit,
    importItems: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage = stringResource(R.string.collection_validation_rules)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ThreeIconButtonsTopBar(
                firstIcon = ImageVector.vectorResource(R.drawable.download),
                secondIcon = Icons.Default.Clear,
                thirdIcon = Icons.Default.Check,
                firstIconContentDescription = stringResource(R.string.import_icon_description),
                secondIconContentDescription = stringResource(id = R.string.cancel_button_description),
                thirdIconContentDescription = stringResource(id = R.string.confirm_button_description),
                firstButtonOnClick = { onShowImport(true) },
                secondButtonOnClick = navigateBack,
                thirdButtonOnClick = {
                    if (collectionUiState.isValid) {
                        saveCollection()
                        navigateBack()
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                snackbarMessage
                            )
                        }
                    }
                }
            )
        })

    { innerPadding ->

        CollectionForm(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = innerPadding.calculateTopPadding()
            ),
            CollectionFormInformation(
                name = collectionUiState.name,
                items = collectionUiState.items,
                newItem = collectionUiState.newItem
            ),
            onNameChange,
            onNewItemChange,
            onAddItem,
            onChangeItem,
            onEditItem
        )

    }

    when {
        collectionUiState.showImportDialog -> ImportCollectionDialog(
            collections = collectionUiState.collectionsToImport.map { it.collection.name },
            onSelected = importItems,
            onDismiss = { onShowImport(false) })
    }
}


@Preview(showBackground = true)
@Composable
fun CreateCollectionScreenPreview() {
    CollectionCreationScreen(
        collectionUiState = CollectionCreationUiState(),
        onNameChange = {},
        onNewItemChange = {},
        onChangeItem = { _, _ -> },
        onEditItem = {},
        saveCollection = { },
        onAddItem = { },
        navigateBack = { },
        onShowImport = {}
    ) {

    }
}

@Composable
private fun ImportCollectionDialog(
    collections: List<String>,
    onSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text(
            text = stringResource(R.string.import_items_message),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )},
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.dismiss), color = MaterialTheme.colorScheme.primary)
            }
        },
        shape =  MaterialTheme.shapes.small,
        text = {
            if (collections.isNotEmpty()) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    itemsIndexed(collections) { index, collection ->
                        ListItem(
                            headlineContent = { Text(text = collection, color = MaterialTheme.colorScheme.onSecondaryContainer) },
                            modifier = Modifier
                                .border(1.dp, MaterialTheme.colorScheme.outline)
                                .clickable {
                                    onSelected(index)
                                    onDismiss()
                                },
                            colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        )
                    }
                }
            } else {
                Text(
                    text = stringResource(R.string.no_collections_found),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }


    )

}

