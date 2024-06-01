package com.example.packassist.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.packassist.ui.screens.collections.CollectionEditScreen
import com.example.packassist.ui.screens.collections.CollectionEditViewModel

const val collectionIdArg = "collectionId"
private const val CollectionEditRoute = "collectionCreation/{$collectionIdArg}"

fun NavGraphBuilder.collectionEditScreen(
    onBack: () -> Unit
) {
    composable(
        route = CollectionEditRoute,
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
    this.navigate(CollectionEditRoute.replace("{$collectionIdArg}", collectionId.toString()))
}
