package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.packassist.R
import com.example.packassist.ui.components.TextAndIconButtonBar

/*TODO delete*/
data class CollectionLocal(
    val id: Int = 0,
    val name: String = ""
)


@Composable
fun CollectionListScreen(
    modifier: Modifier = Modifier
    // todo viewModel
) {
    val collectionList = listOf(
        CollectionLocal(5, "Kolekcia1"),
        CollectionLocal(6, "Kolekcia2"),
        CollectionLocal(8, "Kolekcia3"),
        CollectionLocal(125, "Kolekcia19"),
        CollectionLocal(1, "Kolekcia20"),
    )

    Scaffold(
        topBar = {
            TextAndIconButtonBar(
                text = stringResource(R.string.collections_screeen_name),
                icon = Icons.Default.Add,
                iconContentDescription = stringResource(R.string.add_new_button_description),
                buttonOnClick = {/*TODO*/}
            )

        },
        modifier = modifier
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.padding(it)
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
fun EventsListsPreview() {
    CollectionListScreen()
}

