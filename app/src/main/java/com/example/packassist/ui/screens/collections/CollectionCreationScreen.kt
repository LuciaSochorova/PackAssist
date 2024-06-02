package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.packassist.R
import com.example.packassist.ui.components.CollectionForm
import com.example.packassist.ui.components.CollectionFormInformation
import com.example.packassist.ui.components.ScreenErrorMessage
import com.example.packassist.ui.components.ThreeIconButtonsTopBar

@Composable
fun CollectionCreationScreen(
    collectionUiState: CollectionCreationUiState,
    onNameChange: (String) -> Unit,
    onNewItemChange: (String) -> Unit,
    onChangeItem: (String, Int) -> Unit,
    inputItemAction: (Int) -> Unit,
    saveCollection: () -> Unit,
    addItemAction: () -> Unit,
    navigateBack: () -> Unit,
    onShowImport: (Boolean) -> Unit,
    importItems: (Int) -> Unit
) {
    
    Scaffold(topBar = {
        ThreeIconButtonsTopBar(
            firstIcon = ImageVector.vectorResource(R.drawable.download),
            secondIcon = Icons.Default.Clear,
            thirdIcon = Icons.Default.Check,
            firstIconContentDescription = stringResource(R.string.import_icon_description),
            secondIconContentDescription = stringResource(id = R.string.cancel_button_description),
            thirdIconContentDescription = stringResource(id = R.string.confirm_button_description),
            firstButtonOnClick = {onShowImport(true)},
            secondButtonOnClick = navigateBack,
            thirdButtonOnClick = {
                if  (collectionUiState.isValid) {
                    saveCollection()
                    navigateBack()
                } else {
                    /*TODo*/
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
            addItemAction,
            onChangeItem,
            inputItemAction
        )

    }

    when{
        collectionUiState.showImportDialog -> importCollectionDialog(
            collections = collectionUiState.collectionsToImport.map { it.collection.name },
            onSelected = importItems,
            onDismiss = { onShowImport(false) })
    }
}



@Preview(showBackground = true)
@Composable
fun CreateCollectionScreenPreview() {

}

@Composable
private fun importCollectionDialog(
    collections: List<String>,
    onSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
){
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
            if (collections.isNotEmpty()) {
                LazyColumn {
                    itemsIndexed(collections){ index, collection ->
                        ListItem(
                            headlineContent = { Text(text = collection) },
                            modifier = Modifier.clickable {
                                onSelected(index)
                                onDismiss()
                            }
                        )
                        HorizontalDivider()
                    }
                }
            } else {
                ScreenErrorMessage(text = stringResource(R.string.no_collections_found))
            }
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.dismiss))
            }

        }

        
    }
}

