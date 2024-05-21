package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R

@Composable
fun TextInput(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardAction: (KeyboardActionScope) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
) {


    OutlinedTextField(
        value = value,
        label = {Text(text = label)},
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = keyboardAction),
        shape = RoundedCornerShape(0.dp)
    )

}


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
        mutableStateListOf<String>("spacák", "karimatka", "vankúš")
    }
    var testText by remember {
        mutableStateOf("Meno")
    }


    Scaffold(

        topBar = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    Row(modifier = Modifier.weight(1f)) {
                        IconButton(
                            onClick = deleteAction,
                            modifier = Modifier
                                .wrapContentSize()
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_button_description),
                                modifier = Modifier.fillMaxSize()
                            )

                        }
                    }
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)) {
                        IconButton(
                            onClick = cancelAction,
                            modifier = Modifier
                                .wrapContentSize()
                        ) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = stringResource(R.string.cancel_button_description),
                                modifier = Modifier.fillMaxSize()
                            )

                        }

                        IconButton(
                            onClick = confirmAction,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 36.dp)
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = stringResource(R.string.confirm_button_description),
                                modifier = Modifier.fillMaxSize()
                            )

                        }
                    }
                }


                HorizontalDivider()
            }
        }) { innerPadding ->
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = innerPadding.calculateTopPadding()
            )
        ) {

            TextInput(
                value = testText,
                onValueChange = {testText = it},
                keyboardAction = {/* todo */},
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            /*
            OutlinedTextField(
                value = testText,
                onValueChange = { testText = it },
                modifier = localModifier.padding(top = 16.dp, bottom = 16.dp),
                textStyle = MaterialTheme.typography.titleLarge,
                leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                // todo keyboardActions = KeyboardActions(onDone = )
            )

             */
            TextInput(
                value = newItem,
                label = "Type new item",
                onValueChange = { newItem = it },
                keyboardAction = {
                    if (newItem.isNotEmpty()) itemsString.add(
                        newItem
                    )
                    focusManager.clearFocus()
                    newItem = ""
                }
            )
            LazyColumn() {
                items(itemsString.size) { index ->
                    TextInput(
                        value = itemsString[index],
                        onValueChange = { itemsString[index] = it },
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
