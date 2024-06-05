package com.example.packassist.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.collections.CollectionEditScreen
import com.example.packassist.ui.screens.collections.CollectionEditViewModel

const val COLLECTION_ID_ARG = "collectionId"
private const val COLLECTION_EDIT_ROUTE = "collectionCreation/{$COLLECTION_ID_ARG}"

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
            inputItemAction = viewModel::ifEmptyDeleteItem,
            saveCollection = viewModel::saveCollection,
            addItemAction = viewModel::addItem,
            onDelete = viewModel::deleteCollection,
            navigateBack = onBack
        )

    }
}

fun NavController.navigateToCollectionEdit(collectionId : Int){
    this.navigate(COLLECTION_EDIT_ROUTE.replace("{$COLLECTION_ID_ARG}", collectionId.toString()))
}
