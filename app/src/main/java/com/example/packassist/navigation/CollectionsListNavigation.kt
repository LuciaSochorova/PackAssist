package com.example.packassist.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.collections.CollectionListScreen
import com.example.packassist.ui.screens.collections.CollectionsListViewModel


//private const val collectionIdArgs = "collectionId"
const val CollectionsListRoute = "collections"
fun NavGraphBuilder.collectionsListScreen(
    onAddNewCollection: () -> Unit,
    onEditCollection: (collectionId: Int) -> Unit
) {
    composable(route = CollectionsListRoute) {
        val viewModel: CollectionsListViewModel =
            viewModel(factory = CollectionsListViewModel.Factory)
        val uiState by viewModel.collectionsListUiState.collectAsState()
        CollectionListScreen(
            uiState = uiState,
            onAddNewCollection = onAddNewCollection,
            onEditCollection = onEditCollection
        )

    }
}

fun NavController.navigateToCollectionsList(navOptions: NavOptions? = null) {
    this.navigate(CollectionsListRoute, navOptions)
}

