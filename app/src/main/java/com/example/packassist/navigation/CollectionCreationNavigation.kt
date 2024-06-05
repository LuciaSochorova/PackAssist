package com.example.packassist.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.packassist.ui.screens.collections.CollectionCreationScreen
import com.example.packassist.ui.screens.collections.CollectionCreationViewModel

private const val COLLECTION_CREATION_ROUTE = "collectionCreation?eventId={$EVENT_ID_ARG}"

fun NavGraphBuilder.collectionCreationScreen(
    onNavigateBack: () -> Unit
) {
    composable(
        route = COLLECTION_CREATION_ROUTE,
        arguments = listOf(navArgument(EVENT_ID_ARG) { nullable = true })
    ) {
        val viewModel: CollectionCreationViewModel =
            viewModel(factory = CollectionCreationViewModel.Factory)
        val uiState = viewModel.state

        CollectionCreationScreen(
            collectionUiState = uiState,
            onNameChange = viewModel::onNameChange,
            onNewItemChange = viewModel::onNewItemChange,
            onChangeItem = viewModel::onChangeExistingItem,
            saveCollection = viewModel::saveCollection,
            onEditItem = viewModel::ifEmptyDeleteItem,
            onAddItem = viewModel::addItem,
            navigateBack = onNavigateBack,
            onShowImport = viewModel::showImportDialog,
            importItems = viewModel::importItemsFromCollection,
            filterImportOptions = viewModel::filterImportCollections
        )

    }
}


fun NavController.navigateToCollectionCreation(eventId: Int? = null) {
    if (eventId != null)
        this.navigate(COLLECTION_CREATION_ROUTE.replace("{$EVENT_ID_ARG}", eventId.toString()))
    else
        this.navigate(COLLECTION_CREATION_ROUTE)

}







