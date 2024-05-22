package com.example.packassist.ui.screens.events

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
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
import com.example.packassist.ui.screens.collections.CollectionField

@Composable
fun ManageEventCollectionsScreen(
    backAction: () -> Unit,
    eventName: String,
    addAction: () -> Unit
) {

    val collectionList = listOf(
        Collection(5, "Kolekcia1"),
        Collection(6, "Kolekcia2"),
        Collection(8, "Kolekcia3"),
        Collection(125, "Kolekcia19"),
        Collection(1, "Kolekcia20"),
    )


    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = addAction, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_new_button_description)
                )

                Text(text = "Add collection", style = MaterialTheme.typography.titleSmall)
            }
        },
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        .fillMaxWidth()


                ) {

                    IconButton(
                        onClick = backAction,
                        modifier = Modifier
                            .wrapContentSize()

                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_icon_description),
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Text(
                        text = eventName,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                HorizontalDivider()
            }
        }) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = innerPadding.calculateTopPadding()
            )
        ) {
            itemsIndexed(collectionList) { index, item ->
                Spacer(modifier = Modifier.size(8.dp))
                CollectionField(
                    item.name,
                    {/*TODO*/}
                )
                Spacer(modifier = Modifier.size(24.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ManageEventCollectionsScreenPreview() {
    ManageEventCollectionsScreen(backAction = { /*TODO*/ }, eventName = "Pokusnýýýýýýýýýý ýýýýý Event") {

    }
}


