package com.example.packassist.ui.screens.events

import android.icu.text.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
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
import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.ui.components.BottomNavBar
import com.example.packassist.ui.components.ScreenErrorMessage
import com.example.packassist.ui.components.SearchBarAndButtonTopBar


@Composable
fun EventsListScreen(
    uiState: ListOfEventsUiState,
    onAddNewEvent: () -> Unit,
    onEventClick: (Int) -> Unit,
    route: String,
    onNavigateToRoute: (route: String) -> Unit,
    filterEvents: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SearchBarAndButtonTopBar(
                onSearch = filterEvents,
                searchLabel = stringResource(R.string.search_event),
                icon = Icons.Default.Add,
                iconContentDescription = stringResource(R.string.add_new_button_description),
                buttonOnClick = onAddNewEvent
            )
//            TextAndIconButtonTopBar(
//                text = stringResource(R.string.Events_screen_name),
//                icon = Icons.Default.Add,
//                iconContentDescription = stringResource(R.string.add_new_button_description),
//                buttonOnClick = onAddNewEvent
//            )
        },
        bottomBar = {
            BottomNavBar(onNavigateToRoute = onNavigateToRoute, currentRoute = route)
        },
        modifier = modifier
    ) { innerPadding ->
        if (uiState.listOfEvents.isNotEmpty()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
            ) {

                items(uiState.listOfEvents) { event ->
                    Spacer(modifier = modifier.size(8.dp))
                    EventField(
                        event = event,
                        onEventClick = onEventClick
                    )
                    Spacer(modifier = modifier.size(24.dp))

                }
            }
        } else {
            ScreenErrorMessage(
                text = stringResource(R.string.no_events_messege),
                modifier = Modifier.padding(innerPadding)
            )
        }

    }


}

@Composable
private fun EventField(
    event: Event,
    onEventClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.medium
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onEventClick(event.id) }
            .fillMaxWidth()
            .border(
                color = MaterialTheme.colorScheme.outline,
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
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        if (event.location != null || event.date != null || event.notes != null) {
            HorizontalDivider()
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            if (event.location != null) {
                Text(
                    text = event.location,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(2f)

                )
            }
            if (event.date != null) {
                Text(
                    text = DateFormat.getDateInstance(3).format(event.date)
                        .toString(),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (event.notes != null) {
            Text(
                text = event.notes,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventsListsPreview() {
 EventsListScreen(
     uiState = ListOfEventsUiState(),
     onAddNewEvent = {  },
     onEventClick = {  },
     route = "route",
     onNavigateToRoute = {},
     filterEvents = {}
 )
}