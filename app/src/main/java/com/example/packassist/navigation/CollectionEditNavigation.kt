package com.example.packassist.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.collections.CollectionEditScreen
import com.example.packassist.ui.screens.collections.CollectionEditViewModel

/**
 * The name of the argument used to pass the collection ID to the collection edit screen.
 */
const val COLLECTION_ID_ARG = "collectionId"
private const val COLLECTION_EDIT_ROUTE = "collectionCreation/{$COLLECTION_ID_ARG}"

/**
 * Adds the collection edit screen to the navigation graph.
 *
 * @param onBack Callback to navigate back to the previous screen.
 * @receiver The NavGraphBuilder object that is used to construct the navigation graph.
 */
fun NavGraphBuilder.collectionEditScreen(
    onBack: () -> Unit
) {
    composable(
        route = COLLECTION_EDIT_ROUTE,
    ) {
        val viewModel: CollectionEditViewModel =
            viewModel(factory = CollectionEditViewModel.Factory)

        val uiState = viewModel.state

        CollectionEditScreen(
            uiState = uiState,
            onNameChange = viewModel::onNameChange,
            onNewItemChange = viewModel::onNewItemChange,
            onChangeItem = viewModel::onChangeExistingItem,
            onEditItem = viewModel::ifEmptyDeleteItem,
            saveCollection = viewModel::saveCollection,
            addItemAction = viewModel::addItem,
            onDelete = viewModel::deleteCollection,
            navigateBack = onBack
        )

    }
}

/**
 * Navigates to the collection edit screen.
 *
 * @param collectionId The ID of the collection to edit.
 * @receiver The NavController object that is used to navigate between screens.
 */
fun NavController.navigateToCollectionEdit(collectionId : Int){
    this.navigate(COLLECTION_EDIT_ROUTE.replace("{$COLLECTION_ID_ARG}", collectionId.toString()))
}
