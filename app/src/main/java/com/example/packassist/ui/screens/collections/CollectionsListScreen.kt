package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.data.Collection


@Composable
fun CollectionListScreen(
    modifier: Modifier = Modifier
    // todo viewModel
) {
    val collectionList = listOf(
        Collection(5, "Kolekcia1"),
        Collection(6, "Kolekcia2"),
        Collection(8, "Kolekcia3"),
        Collection(125, "Kolekcia19"),
        Collection(1, "Kolekcia20"),
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
                        text = stringResource(R.string.collections_screeen_name),
                        style = MaterialTheme.typography.displayMedium,
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .wrapContentSize()
                            .border(
                                color = MaterialTheme.colorScheme.scrim,
                                width = 1.dp
                            )
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_new_button_description),
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
            modifier = Modifier.padding(it)
        ) {

            itemsIndexed(collectionList) { index, item ->
                Spacer(modifier = modifier.size(8.dp))
                CollectionField(
                    item.name,
                    {/*TODO*/}
                    )
                Spacer(modifier = modifier.size(24.dp))
            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun EventsListsPreview() {
    CollectionListScreen()
}

