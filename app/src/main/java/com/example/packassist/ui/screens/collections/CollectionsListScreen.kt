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
import com.example.packassist.ui.components.CollectionCard
import com.example.packassist.ui.components.ScreenErrorMessage
import com.example.packassist.ui.components.SearchBarAndButtonTopBar


/**
 * A composable function that displays screen with all collections without event.
 *
 * @param uiState The current state of the screen.
 * @param onAddNewCollection A callback to be invoked when the user wants to add a new collection.
 * @param onEditCollection A callback to be invoked when the user wants to edit a collection.
 * @param onNavigateToRoute A callback to be invoked when the user wants to navigate to a different screen.
 * @param filterCollections A callback to be invoked when the user wants to filter (search) the collections.
 * @param route The route of the screen.
 * @param modifier The modifier to be applied to the screen.
 */
@Composable
fun CollectionListScreen(
    uiState: CollectionsListUiState,
    onAddNewCollection: () -> Unit,
    onEditCollection: (collectionId: Int) -> Unit,
    onNavigateToRoute: (route: String) -> Unit,
    filterCollections: (String) -> Unit,
    route: String,
    modifier: Modifier = Modifier

) {

    Scaffold(
        topBar = {
            SearchBarAndButtonTopBar(
                onSearch = filterCollections,
                searchLabel = stringResource(id = R.string.search_collection),
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
        if (uiState.collectionList.isNotEmpty()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
            ) {
                items(uiState.collectionList) { collection ->
                    Spacer(modifier = Modifier.size(8.dp))
                    CollectionCard(
                        collection, onEditCollection
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                }
            }


        } else {
            ScreenErrorMessage(
                stringResource(R.string.no_collections),
                Modifier.padding(innerPadding)
            )
        }


    }
}


/**
 * Events lists preview
 *
 */
@Preview(showBackground = true)
@Composable
fun EventsListsPreview() {
    CollectionListScreen(
        uiState = CollectionsListUiState(),
        route = NavigationBarRoutes.COLLECTIONS.name,
        onAddNewCollection = { },
        onEditCollection = {},
        onNavigateToRoute = {},
        filterCollections = {}
    )
}

