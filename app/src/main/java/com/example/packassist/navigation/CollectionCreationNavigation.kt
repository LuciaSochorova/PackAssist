package com.example.packassist.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.packassist.ui.screens.collections.CollectionCreationScreen
import com.example.packassist.ui.screens.collections.CollectionCreationViewModel

const val CollectionCreationRoute = "collectionCreation?eventId={$EventIdArg}"

fun NavGraphBuilder.collectionCreationScreen(
    onNavigateBack: () -> Unit
) {
    composable(
        route = CollectionCreationRoute,
        arguments = listOf(navArgument(EventIdArg) { nullable = true })
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
        this.navigate(CollectionCreationRoute.replace("{$EventIdArg}", eventId.toString()))
    else
        this.navigate(CollectionCreationRoute)

}







