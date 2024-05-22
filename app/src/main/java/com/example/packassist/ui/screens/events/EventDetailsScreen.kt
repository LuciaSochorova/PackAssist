package com.example.packassist.ui.screens.events

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.data.Collection
import com.example.packassist.data.Event
import com.example.packassist.data.Item
import java.time.LocalDate


data class CollectionItems(val collection: Collection, val items: List<Item>)
@Composable
fun EventDetailsScreen(
    backAction: () -> Unit,
    deleteAction: () -> Unit,
    floatingButtonAction: () -> Unit
) {
    val normalFont = MaterialTheme.typography.bodyMedium
    val kolekcie = listOf(CollectionItems(
        Collection(name = "Bla bla bla"),
        listOf(Item("Spacák", 0), Item("Taška", 0))
    ))
    val focusManager = LocalFocusManager.current
    val event =
        Event(name = "Event pokus", date = LocalDate.of(2024, 5, 2), notes = null, location = null)
    var eventName by remember {
        mutableStateOf("Event pokus kjh hiu hiuhuih uiui")
    }


    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = floatingButtonAction, containerColor = MaterialTheme.colorScheme.primary) {
            Icon(
                Icons.Default.Edit,
                contentDescription = stringResource(id = R.string.edit_button_description)
            )

            Text(text = "Manage Collections", style = MaterialTheme.typography.titleSmall)
        }},
        floatingActionButtonPosition = FabPosition.Start,

        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        .fillMaxWidth()


                ) {
                    IconButton(
                        onClick = backAction,
                        modifier = Modifier
                            .wrapContentSize()
                            .weight(1f)

                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "" /*TODO*/,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    TextField(
                        value = eventName,
                        onValueChange = {
                            if (it.isNotEmpty()) {
                                eventName = it
                            }
                        },
                        textStyle = MaterialTheme.typography.displaySmall,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        modifier = Modifier
                            .weight(5f)
                    )

                    IconButton(
                        onClick = deleteAction,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize()

                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_button_description),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                HorizontalDivider()
            }

        })
    { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = innerPadding.calculateTopPadding()
            )
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max)
                    ) {
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                .weight(3f)
                        ) {
                            TextField(
                                modifier = Modifier.fillMaxHeight(),
                                value = event.location ?: "",
                                label = {
                                    if (event.location.isNullOrBlank()) {
                                        Text(text = "Location", style = normalFont)
                                    }
                                },
                                onValueChange = {/*TODO*/ },
                                textStyle = normalFont,
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                ),
                            )
                        }
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                .weight(2f)
                        ) {

                            OutlinedButton(
                                onClick = { /*TODO*/ },
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors()
                                    .copy(contentColor = MaterialTheme.colorScheme.onSurface),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth()

                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),

                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = event.date?.toString() ?: "Date",
                                        style = normalFont,
                                        modifier = Modifier.weight(4f),
                                    )
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(start = 4.dp)
                                            .weight(1f)
                                    )
                                }

                            }
                        }

                    }

                    Text(
                        text = event.notes ?: "Type new",
                        textAlign = TextAlign.Center,
                        style = normalFont,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface)
                            .defaultMinSize(minHeight = 48.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)) }


            itemsIndexed(kolekcie){ index, item ->
                ExpandingListOfItems(coll = item, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp))
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun EventDetailsScreenPreview() {
    EventDetailsScreen({}, {}, {})
}




@Composable
fun ExpandingListOfItems(
    coll: CollectionItems,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember {
        mutableStateOf(true)
    }

    Column(modifier = modifier.border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface)) {
        ListItem(
            headlineContent = {
                Text(
                    text = coll.collection.name,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            trailingContent = {
                IconButton(onClick = { isExpanded = !(isExpanded) }) {
                    if (isExpanded) {
                        Icon(
                            Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                        )
                    } else {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                        )
                    }
                }

            },
            modifier = Modifier.fillMaxWidth()

        )



        if (isExpanded) {
            for (item in coll.items) {
                HorizontalDivider()
                ListItem(
                    headlineContent = {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    trailingContent = {
                        Checkbox(checked = item.packed, onCheckedChange = {/*TODO*/ })

                    }
                    )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ExpandingListOfItemsPreview() {
    ExpandingListOfItems(
        coll = CollectionItems(
            Collection(name = "Bla bla bla"),
            listOf(Item("Spacák", 0), Item("Taška", 0))
        )
    )
}

