package com.example.packassist.ui.screens.events

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.data.Event
import java.time.LocalDate


@Composable
fun EventsListScreen(
    modifier: Modifier = Modifier
    // todo viewModel
) {
    val eventlist = listOf(
        Event(id = 1, name = "Event1", null, null, "notes, notes, notes, notes..........."),
        Event(
            id = 2,
            name = "Event2",
            "Somewhere",
            LocalDate.of(2024, 5, 2),
            "notes, notes, notes, notes..........."
        )
    )

    Scaffold(
        topBar = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .fillMaxWidth()



                ) {
                    Text(
                        text = stringResource(R.string.Events_screen_name),
                        style = MaterialTheme.typography.displayMedium,
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .wrapContentSize()
                            .border(
                                color = MaterialTheme.colorScheme.scrim,
                                width = 2.dp
                            )
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_new_event_button_description),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                HorizontalDivider()
            }

        },
        modifier = modifier
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.padding(top = (it.calculateTopPadding() + 32.dp))
        ) {

            itemsIndexed(eventlist) { index, item ->
                EventField(
                    event = item,
                    onItemClick = { event -> //Navigation.current.navigate("productDetail/${event.id}
                    })
                Spacer(modifier = modifier.size(32.dp))
            }
        }

    }


}

@Composable
fun EventField(
    event: Event,
    onItemClick: (Event) -> Unit,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.medium
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onItemClick }
            .fillMaxWidth()
            .border(
                color = MaterialTheme.colorScheme.scrim,
                width = 2.dp,
                shape = shape
            )
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = shape
            )
            .padding(start = 8.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)


    ) {

        Text(
            text = event.name,
            style = MaterialTheme.typography.headlineLarge,
        )
        if (event.location != null || event.date != null || event.notes != null) {
            HorizontalDivider()
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            if (event.location != null) {
                Text(
                    text = event.location,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(3f)

                )
            }
            if (event.date != null) {
                Text(
                    text = event.date.toString(),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (event.notes != null) {
            Text(
                text = event.notes,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventsListsPreview() {
    EventsListScreen()
}