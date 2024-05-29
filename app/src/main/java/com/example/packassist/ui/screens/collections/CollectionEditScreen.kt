package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.ui.components.CollectionForm
import com.example.packassist.ui.components.CollectionFormInformation
import com.example.packassist.ui.components.ThreeIconButtonsTopBar


@Composable
fun CollectionEditScreen(
    uiState: CollectionEditUiState,
    onNameChange: (String) -> Unit,
    onNewItemChange: (String) -> Unit,
    onChangeItem: (String, Int) -> Unit,
    inputItemAction: (Int) -> Unit,
    saveCollection: () -> Unit,
    addItemAction: () -> Unit,
    navigateBack: () -> Unit,
    onDelete: () -> Unit
) {


    Scaffold(

        topBar = {
            ThreeIconButtonsTopBar(
                firstIcon = Icons.Default.Delete,
                secondIcon = Icons.Default.Clear,
                thirdIcon = Icons.Default.Check,
                firstButtonOnClick = {
                    navigateBack()
                    onDelete()

                }, /* todo potvrdzovací dialog*/
                secondButtonOnClick = navigateBack,
                thirdButtonOnClick = {
                    if (uiState.isValid) {
                        navigateBack()
                        saveCollection()
                    } else {
                        /*TODo*/
                    }
                },
                firstIconContentDescription = stringResource(R.string.delete_button_description),
                secondIconContentDescription = stringResource(R.string.cancel_button_description),
                thirdIconContentDescription = stringResource(R.string.confirm_button_description)
            )
        }) { innerPadding ->
        CollectionForm(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = innerPadding.calculateTopPadding()
            ),
            CollectionFormInformation(
                name = uiState.name,
                newItem = uiState.newItem,
                items = uiState.items.map { it.second },
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
fun CollectionEditScreenPreview() {

}
