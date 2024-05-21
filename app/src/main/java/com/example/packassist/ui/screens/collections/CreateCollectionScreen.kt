package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.ui.components.TextInputField
import com.example.packassist.ui.components.ThreeIconButtonsBar

@Composable
fun CreateCollectionScreen(

) {
    var newItem by remember {
        mutableStateOf("")
    }
    val itemsString = remember {
        mutableStateListOf<String>()
    }
    var nameText by remember {
        mutableStateOf("")
    }
    Scaffold(topBar = {
        ThreeIconButtonsBar(
            firstIcon = ImageVector.vectorResource(R.drawable.download),
            secondIcon = Icons.Default.Clear,
            thirdIcon = Icons.Default.Check,
            firstButtonOnClick = { /*TODO*/ },
            secondButtonOnClick = { /*TODO*/ },
            thirdButtonOnClick = { /*TODO*/ })
    })

    { innerPadding ->
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = innerPadding.calculateTopPadding()
            )
        ) {
            TextInputField(
                value = nameText,
                onValueChange = { nameText = it },
                keyboardAction = { focusManager.clearFocus() },
                label = stringResource(R.string.type_name_label),
                textStyle = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            TextInputField(
                value = newItem,
                onValueChange = { newItem = it },
                keyboardAction = {
                    focusManager.clearFocus()
                    if (newItem.isNotEmpty()) itemsString.add(newItem)
                    newItem = ""
                },
                label = stringResource(R.string.type_new_item_label),
                textStyle = MaterialTheme.typography.bodyLarge
            )

            LazyColumn {
                items(itemsString.size) {index ->
                    TextInputField(
                        value = itemsString[index],
                        onValueChange = {itemsString[index] = it},
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
fun CreateCollectionScreenPreview() {
    CreateCollectionScreen()
}

