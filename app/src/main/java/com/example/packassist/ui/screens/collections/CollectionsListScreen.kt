package com.example.packassist.ui.screens.collections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.packassist.navigation.NavigationBarRoutes
import com.example.packassist.ui.components.BottomNavBar
import com.example.packassist.ui.components.CollectionField
import com.example.packassist.ui.components.ScreenErrorMessage
import com.example.packassist.ui.components.TextAndIconButtonTopBar

/*TODO delete*/
data class CollectionLocal(
    val id: Int = 0, val name: String = ""
)



@Composable
fun CollectionListScreen(
    uiState: CollectionsListUiState,
    onAddNewCollection: () -> Unit,
    onEditCollection: (collectionId: Int) -> Unit,
    onNavigateToRoute: (route: String) -> Unit,
    route: String,
    modifier: Modifier = Modifier

) {

    Scaffold(
        topBar = {


            TextAndIconButtonTopBar(
                text = stringResource(R.string.collections_screeen_name),
                icon = Icons.Default.Add,
                iconContentDescription = stringResource(R.string.add_new_button_description),
                buttonOnClick = onAddNewCollection
            )


        },


        bottomBar = {
            BottomNavBar(onNavigateToRoute = onNavigateToRoute, currentRoute = route)
        },


        modifier = modifier
    ) { innerPadding ->
        if (uiState.collList.isNotEmpty()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
            ) {


                items(uiState.collList) { item ->
                    Spacer(modifier = Modifier.size(8.dp))
                    CollectionField(
                        item, onEditCollection
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                }

            }

        } else {
            ScreenErrorMessage(stringResource(R.string.no_collections) ,Modifier.padding(innerPadding))

        }


    }
}


@Preview(showBackground = true)
@Composable
fun EventsListsPreview() {
    CollectionListScreen(
        uiState = CollectionsListUiState(),
        route = NavigationBarRoutes.COLLECTIONS.name,
        onAddNewCollection = { /*TODO*/ },
        onEditCollection = {},
        onNavigateToRoute = {})
}

