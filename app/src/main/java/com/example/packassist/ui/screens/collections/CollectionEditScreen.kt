package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.ui.components.TextInputField
import com.example.packassist.ui.components.ThreeIconButtonsBar


@Composable
fun CollectionEditScreen(
    cancelAction: () -> Unit,
    confirmAction: () -> Unit,
    deleteAction: () -> Unit
) {
    var newItem by remember {
        mutableStateOf("")
    }
    val itemsString = remember {
        mutableStateListOf("spacák", "karimatka", "vankúš")
    }
    var testText by remember {
        mutableStateOf("Meno")
    }


    Scaffold(

        topBar = {
            ThreeIconButtonsBar(
                firstIcon = Icons.Default.Delete,
                secondIcon = Icons.Default.Clear,
                thirdIcon = Icons.Default.Check,
                firstButtonOnClick = deleteAction,
                secondButtonOnClick = cancelAction,
                thirdButtonOnClick = confirmAction,
                firstIconContentDescription = stringResource(R.string.delete_button_description),
                secondIconContentDescription = stringResource(R.string.cancel_button_description),
                thirdIconContentDescription = stringResource(R.string.confirm_button_description))
        }) { innerPadding ->
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = innerPadding.calculateTopPadding()
            )
        ) {

            TextInputField(
                value = testText,
                onValueChange = {testText = it},
                keyboardAction = {/* todo */},
                textStyle = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            TextInputField(
                value = newItem,
                label = stringResource(R.string.type_new_item_label),
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = { newItem = it },
                keyboardAction = {
                    if (newItem.isNotEmpty()) itemsString.add(
                        newItem
                    )
                    focusManager.clearFocus()
                    newItem = ""
                }
            )
            LazyColumn {
                items(itemsString.size) { index ->
                    TextInputField(
                        value = itemsString[index],
                        onValueChange = { itemsString[index] = it },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        keyboardAction = {
                            if (itemsString[index].isEmpty()) itemsString.remove(
                                itemsString[index]
                            )
                            focusManager.clearFocus()
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CollectionEditScreenPreview() {
    CollectionEditScreen(cancelAction = { /*TODO*/ }, confirmAction = { /*TODO*/ }) {

    }
}
