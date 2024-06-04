package com.example.packassist.ui.screens.events

import android.icu.text.DateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.packassist.R
import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.ui.components.ConfirmationDialog
import com.example.packassist.ui.components.TextEditField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    uiState: EventDetailsUiState,
    onEventNameChange: (String) -> Unit,
    onItemCheckedChange: (Item) -> Unit,
    saveChanges: () -> Unit,
    navigateBack: () -> Unit,
    deleteAction: () -> Unit,
    onLocationChange: (String) -> Unit,
    onWriteNotes: (Boolean) -> Unit,
    onNotesChange: (String) -> Unit,
    onPickDate: (Boolean) -> Unit,
    onDateChange: (Long?) -> Unit,
    floatingButtonAction: (Int) -> Unit,
    changeExpand: (id: Int) -> Unit
) {
    val normalFont = MaterialTheme.typography.bodyMedium
    val focusManager = LocalFocusManager.current
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    saveChanges()
                    floatingButtonAction(uiState.event.id)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_button_description)
                )

                Text(
                    text = stringResource(R.string.manage_collections),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Start,

        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        .fillMaxWidth()


                ) {
                    IconButton(
                        onClick = {
                            if (uiState.event.name.isNotBlank()) {
                                navigateBack()
                                saveChanges()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_icon_description),
                            modifier = Modifier.fillMaxSize(0.8f)
                        )
                    }

                    TextEditField(
                        value = uiState.event.name,
                        onValueChange = { onEventNameChange(it) },
                        keyboardAction = { focusManager.clearFocus() },
                        isError = uiState.event.name.isBlank(),
                        textStyle = MaterialTheme.typography.headlineSmall,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier
                            .weight(5f)
                    )


                    IconButton(
                        onClick = {
                            showDeleteDialog = true
                        },
                        modifier = Modifier
                            .weight(1f)


                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_button_description),
                            modifier = Modifier.fillMaxSize(0.8f)
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
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {
            item {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max)
                        ) {
                            TextField(
                                value = uiState.event.location ?: "",
                                label = {
                                    if (uiState.event.location.isNullOrBlank()) {
                                        Text(
                                            text = stringResource(R.string.location),
                                            style = normalFont
                                        )
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Edit,
                                        contentDescription = null
                                    )
                                },
                                onValueChange = onLocationChange,
                                textStyle = normalFont,
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                colors = OutlinedTextFieldDefaults.colors(),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                    .weight(3f),
                            )

                            OutlinedButton(
                                onClick = {
                                    focusManager.clearFocus()
                                    onPickDate(true)
                                },
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors()
                                    .copy(contentColor = MaterialTheme.colorScheme.onSurface),
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                    .weight(2f)
                                    .fillMaxHeight()
                                    .fillMaxWidth()


                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),

                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {

                                    val text: String = if (uiState.event.date != null) {
                                        DateFormat.getDateInstance(3).format(uiState.event.date)
                                            .toString()
                                    } else {
                                        stringResource(R.string.date)
                                    }

                                    Text(
                                        text = text,
                                        style = normalFont,
                                        modifier = Modifier.weight(5f),
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

                        Text(
                            text = uiState.event.notes ?: stringResource(R.string.type_notes),
                            textAlign = TextAlign.Center,
                            style = normalFont,
                            modifier = Modifier
                                .clickable {
                                    focusManager.clearFocus()
                                    onWriteNotes(true)
                                }
                                .fillMaxWidth()
                                .border(width = 1.dp, color = MaterialTheme.colorScheme.outline)
                                .defaultMinSize(minHeight = 48.dp)
                                .padding(12.dp)
                        )
                    }
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                )
            }


            if (uiState.collections.isNotEmpty()) {
                items(items = uiState.collections,
                    key = { it.first.collectionId}) { collection ->
                    ExpandingListOfItems(
                        coll = collection.first,
                        isExpanded = collection.second,
                        onCheckedChange = onItemCheckedChange,
                        captionColors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            headlineColor = MaterialTheme.colorScheme.primary

                        ),
                        listColors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        changeExpand = changeExpand,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)

                    )
                }
            }

            item { Spacer(modifier = Modifier.size(64.dp)) }


        }
    }

    when {
        uiState.event.writingNotes ->
            NotesInputDialog(
                value = uiState.event.notes ?: "",
                onValueChange = { onNotesChange(it) },
                onDismiss = { onWriteNotes(false) },
                modifier = Modifier.fillMaxSize()
            )

        uiState.event.pickingDate ->
            DateInputDialog(
                onConfirm = { onDateChange(it) },
                onDismiss = { onPickDate(false) },
                date = uiState.event.date?.time
            )

        showDeleteDialog ->
            ConfirmationDialog(
                text = stringResource(R.string.delete_event_confirmation_message),
                onDismiss = { showDeleteDialog = false },
                onConfirm = {
                    navigateBack()
                    deleteAction()
                })
    }


}

@Preview(showBackground = true)
@Composable
fun EventDetailsScreenPreview() {
    EventDetailsScreen(EventDetailsUiState(), {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {},{})
}


@Composable
private fun ExpandingListOfItems(
    coll: CollectionItems,
    onCheckedChange: (Item) -> Unit,
    isExpanded: Boolean,
    changeExpand: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    captionColors: ListItemColors = ListItemDefaults.colors(),
    listColors: ListItemColors = ListItemDefaults.colors()
) {

    Surface(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier
    ) {
        Column {
            ListItem(
                headlineContent = {
                    Row {
                        Text(
                            modifier = Modifier.padding(end = 8.dp),
                            text = coll.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        if (coll.items.all { it.packed }) {
                            Icon(
                                Icons.Default.ThumbUp,
                                contentDescription = stringResource(R.string.all_packed),
                            )
                        }
                    }
                },
                colors = captionColors,
                trailingContent = {
                    IconButton(onClick = { changeExpand(coll.collectionId) }) {
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
                        colors = listColors,
                        trailingContent = {
                            Checkbox(
                                checked = item.packed,
                                onCheckedChange = { onCheckedChange(item) })

                        }
                    )

                }
            }
        }
    }

}


@Composable
private fun NotesInputDialog(
    value: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {


    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
    ) {

        Column(
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack, contentDescription = stringResource(
                            id = R.string.back_icon_description
                        )
                    )
                }
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateInputDialog(
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit,
    date: Long? = null,
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date)
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.confirm_button_description))
            }
        }) {
        DatePicker(state = datePickerState)
    }
}
