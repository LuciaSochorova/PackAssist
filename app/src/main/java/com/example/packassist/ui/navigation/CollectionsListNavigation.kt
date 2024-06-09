package com.example.packassist.ui.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.collections.CollectionListScreen
import com.example.packassist.ui.screens.collections.CollectionsListViewModel

/**
 * The route for the collections list screen.
 */
const val COLLECTIONS_LIST_ROUTE = "collections"

/**
 * Adds the collections list screen to the navigation graph.
 *
 * @param onAddNewCollection Callback to navigate to the add new collection screen.
 * @param onEditCollection Callback to navigate to the edit collection screen.
 * @param onNavigateToRoute Callback to navigate to a different route.
 * @receiver The NavGraphBuilder object that is used to construct the navigation graph.
 */
fun NavGraphBuilder.collectionsListScreen(
    onAddNewCollection: () -> Unit,
    onEditCollection: (collectionId: Int) -> Unit,
    onNavigateToRoute: (route: String) -> Unit
) {
    composable(route = COLLECTIONS_LIST_ROUTE) {
        val viewModel: CollectionsListViewModel =
            viewModel(factory = CollectionsListViewModel.Factory)
        val uiState by viewModel.collectionsListUiState.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)
        CollectionListScreen(
            uiState = uiState,
            onAddNewCollection = onAddNewCollection,
            onEditCollection = onEditCollection,
            route = COLLECTIONS_LIST_ROUTE,
            onNavigateToRoute = onNavigateToRoute,
            filterCollections = viewModel::filter
        )

    }
}

/**
 * Navigates to the collections list screen.
 *
 * @receiver The NavController object that is used to navigate between screens.
 */
fun NavController.navigateToCollectionsList() {
    this.navigate(COLLECTIONS_LIST_ROUTE)
}

