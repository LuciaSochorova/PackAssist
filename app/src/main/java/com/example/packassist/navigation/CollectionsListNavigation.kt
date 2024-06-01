package com.example.packassist.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.collections.CollectionListScreen
import com.example.packassist.ui.screens.collections.CollectionsListViewModel


const val CollectionsListRoute = "collections"
fun NavGraphBuilder.collectionsListScreen(
    onAddNewCollection: () -> Unit,
    onEditCollection: (collectionId: Int) -> Unit,
    onNavigateToRoute: (route: String) -> Unit
) {
    composable(route = CollectionsListRoute) {
        val viewModel: CollectionsListViewModel =
            viewModel(factory = CollectionsListViewModel.Factory)
        val uiState by viewModel.collectionsListUiState.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)
        CollectionListScreen(
            uiState = uiState,
            onAddNewCollection = onAddNewCollection,
            onEditCollection = onEditCollection,
            route = CollectionsListRoute,
            onNavigateToRoute = onNavigateToRoute
        )

    }
}

fun NavController.navigateToCollectionsList() {
    this.navigate(CollectionsListRoute)
}

