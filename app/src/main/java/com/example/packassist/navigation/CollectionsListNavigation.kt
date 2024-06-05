package com.example.packassist.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.collections.CollectionListScreen
import com.example.packassist.ui.screens.collections.CollectionsListViewModel


const val COLLECTIONS_LIST_ROUTE = "collections"
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

fun NavController.navigateToCollectionsList() {
    this.navigate(COLLECTIONS_LIST_ROUTE)
}

