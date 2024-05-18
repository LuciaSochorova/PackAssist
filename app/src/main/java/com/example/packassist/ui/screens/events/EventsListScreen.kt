package com.example.packassist.ui.screens.events

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(8.dp)
    ) {

        itemsIndexed(eventlist) { index, item ->
            EventField(event = item, onItemClick = {
                event -> //Navigation.current.navigate("productDetail/${event.id}
            })
            Spacer(modifier = modifier.size(32.dp))
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
                color = Color.Black, width = 2.dp,
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