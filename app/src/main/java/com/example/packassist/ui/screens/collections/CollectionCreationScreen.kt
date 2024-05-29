package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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

@Composable
fun CollectionCreationScreen(
    collectionUiState: CollectionUiState,
    onNameChange: (String) -> Unit,
    onNewItemChange: (String) -> Unit,
    onChangeItem: (String, Int) -> Unit,
    inputItemAction: (Int) -> Unit,
    saveCollection: () -> Unit,
    addItemAction: () -> Unit,
    navigateBack: () -> Unit,
    onImport: () -> Unit
) {
    
    Scaffold(topBar = {
        ThreeIconButtonsTopBar(
            firstIcon = ImageVector.vectorResource(R.drawable.download),
            secondIcon = Icons.Default.Clear,
            thirdIcon = Icons.Default.Check,
            firstIconContentDescription = stringResource(R.string.import_icon_description),
            secondIconContentDescription = stringResource(id = R.string.cancel_button_description),
            thirdIconContentDescription = stringResource(id = R.string.confirm_button_description),
            firstButtonOnClick = onImport,
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
}



@Preview(showBackground = true)
@Composable
fun CreateCollectionScreenPreview() {

}

