package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.ui.components.CollectionForm
import com.example.packassist.ui.components.CollectionFormInformation
import com.example.packassist.ui.components.ConfirmationDialog
import com.example.packassist.ui.components.ThreeIconButtonsTopBar
import kotlinx.coroutines.launch


@Composable
fun CollectionEditScreen(
    uiState: CollectionEditUiState,
    onNameChange: (String) -> Unit,
    onNewItemChange: (String) -> Unit,
    onChangeItem: (String, Int) -> Unit,
    inputItemAction: () -> Unit,
    saveCollection: () -> Unit,
    addItemAction: () -> Unit,
    navigateBack: () -> Unit,
    onDelete: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage = stringResource(R.string.collection_validation_rules)

    var showConfirmDeleteDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ThreeIconButtonsTopBar(
                firstIcon = Icons.Default.Delete,
                secondIcon = Icons.Default.Clear,
                thirdIcon = Icons.Default.Check,
                firstButtonOnClick = {
                    showConfirmDeleteDialog = true
                },
                secondButtonOnClick = navigateBack,
                thirdButtonOnClick = {
                    if (uiState.isValid) {
                        navigateBack()
                        saveCollection()
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                snackbarMessage
                            )
                        }
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
                items = uiState.items.map { it.name },
            ),
            onNameChange,
            onNewItemChange,
            addItemAction,
            onChangeItem,
            inputItemAction
        )
    }


    when {
        showConfirmDeleteDialog -> {
            ConfirmationDialog(
                text = stringResource(R.string.delete_collection_confirmation_messege),
                onDismiss = { showConfirmDeleteDialog = false },
                onConfirm = {
                    navigateBack()
                    onDelete()
                })
        }
    }

}


@Preview(showBackground = true)
@Composable
fun CollectionEditScreenPreview() {

}
